package cz.fi.muni.pv168.todo.storage.sql.dao;

import cz.fi.muni.pv168.todo.storage.sql.db.ConnectionHandler;
import cz.fi.muni.pv168.todo.storage.sql.entity.TemplateEntity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * DAO for {@link TemplateEntity} entity.
 */
public final class TemplateDao implements DataAccessObject<TemplateEntity> {

    private final Supplier<ConnectionHandler> connections;

    public TemplateDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public TemplateEntity create(TemplateEntity newTemplate) {
        var sql = """
                INSERT INTO Template(
                    id,
                    name,
                    categoryId,
                    startTime,
                    durationId,
                    description,
                )
                VALUES (?, ?, ?, ?, ?, ?);
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            UUID id = UUID.randomUUID();
            statement.setString(1, id.toString());
            statement.setString(2, newTemplate.name());
            statement.setString(3, newTemplate.categoryId().toString());
            statement.setDate(4, Date.valueOf(newTemplate.startTime()));
            statement.setString(5, newTemplate.durationId().toString());
            statement.setString(6, newTemplate.description());
            statement.executeUpdate();
            return findById(id).orElseThrow();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newTemplate, ex);
        }
    }

    @Override
    public Collection<TemplateEntity> findAll() {
        var sql = """
                SELECT id,
                    name,
                    categoryId,
                    startTime,
                    durationId,
                    description
                FROM Template
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            List<TemplateEntity> templates = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var template = templateFromResultSet(resultSet);
                    templates.add(template);
                }
            }

            return templates;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all templates", ex);
        }
    }

    @Override
    public Optional<TemplateEntity> findById(UUID id) {
        var sql = """
                SELECT id,
                    name,
                    categoryId,
                    startTime,
                    durationId,
                    description
                FROM Template
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, String.valueOf(id));
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(templateFromResultSet(resultSet));
            } else {
                // template not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load department by id: " + id, ex);
        }
    }

    @Override
    public TemplateEntity update(TemplateEntity entity) {
        var sql = """
                UPDATE Template
                SET name = ?,
                    categoryId = ?,
                    startTime = ?,
                    durationId = ?,
                    description = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.name());
            statement.setString(2, String.valueOf(entity.categoryId()));
            statement.setDate(3, Date.valueOf(entity.startTime()));
            statement.setString(4, String.valueOf(entity.durationId()));
            statement.setString(4, String.valueOf(entity.description()));
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Template not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 template (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update template: " + entity, ex);
        }
    }

    @Override
    public void deleteById(UUID id) {
        var sql = """
                DELETE FROM Template
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, String.valueOf(id));
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Template not found, guid: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 template (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete template guid: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Template";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all templates", ex);
        }
    }

    @Override
    public boolean existsById(UUID id) {
        var sql = """
                SELECT id
                FROM Template
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, String.valueOf(id));
            try (var resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to check if template exists, id: " + id, ex);
        }
    }

    private static TemplateEntity templateFromResultSet(ResultSet resultSet) throws SQLException {
        return new TemplateEntity(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                UUID.fromString(resultSet.getString("categoryId")),
                resultSet.getTimestamp("startTime").toLocalDateTime().toLocalDate(),
                UUID.fromString(resultSet.getString("durationId")),
                resultSet.getString("description")
                );
    }
}
