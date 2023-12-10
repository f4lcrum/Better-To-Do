package cz.fi.muni.pv168.todo.storage.sql.dao;

import cz.fi.muni.pv168.todo.storage.sql.db.ConnectionHandler;
import cz.fi.muni.pv168.todo.storage.sql.entity.EventEntity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public final class EventDao implements DataAccessObject<EventEntity> {

    private final Supplier<ConnectionHandler> connections;

    public EventDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public EventEntity create(EventEntity entity) {
        var sql = """
                INSERT INTO Event(
                    id,
                    name,
                    category,
                    timeUnit,
                    timeUnitCount,
                    startDate,
                    startTime,
                    description
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.id());
            statement.setString(2, entity.name());
            statement.setString(3, entity.categoryId());
            statement.setString(4, entity.timeUnitId());
            statement.setInt(5, entity.timeUnitCount());
            statement.setDate(6, Date.valueOf(entity.date()));
            statement.setTime(7, Time.valueOf(entity.startTime()));
            statement.setString(8, entity.description());
            statement.executeUpdate();

            try (var keyResultSet = statement.getGeneratedKeys()) {
                UUID eventId;

                if (keyResultSet.next()) {
                    eventId = UUID.fromString(keyResultSet.getString(1));
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + entity);
                }

                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + entity);
                }

                return findById(eventId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + entity, ex);
        }
    }

    @Override
    public Collection<EventEntity> findAll() {
        var sql = """
                SELECT
                    id,
                    name,
                    category,
                    timeUnit,
                    timeUnitCount,
                    startDate,
                    startTime,
                    description
                FROM Event
                """;
        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            List<EventEntity> events = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var event = eventFromResultSet(resultSet);
                    events.add(event);
                }
            }

            return events;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all events", ex);
        }

    }

    @Override
    public Optional<EventEntity> findById(UUID id) {
        var sql = """
                SELECT
                    id,
                    name,
                    category,
                    timeUnit,
                    timeUnitCount,
                    startDate,
                    startTime,
                    description
                FROM Event
                WHERE id = ?
                """;
        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, id.toString());

            var resultSet = statement.executeQuery();

            return resultSet.next() ?
                    Optional.of(eventFromResultSet(resultSet)) :
                    Optional.empty();

        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load event by ID", ex);
        }
    }

    @Override
    public EventEntity update(EventEntity entity) {
        var sql = """
                UPDATE Event
                SET name = ?,
                    category = ?,
                    timeUnit = ?,
                    timeUnitCount = ?,
                    startDate = ?,
                    startTime = ?,
                    description = ?
                WHERE id = ?
                """;
        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.name());
            statement.setString(2, entity.categoryId());
            statement.setString(3, entity.timeUnitId());
            statement.setInt(4, entity.timeUnitCount());
            statement.setDate(5, Date.valueOf(entity.date()));
            statement.setTime(6, Time.valueOf(entity.startTime()));
            statement.setString(7, entity.description());
            statement.setString(8, entity.id());

            var rowsUpdatedCount = statement.executeUpdate();

            if (rowsUpdatedCount == 0) {
                throw new DataStorageException("Event not found, sought ID: " + entity.id());
            }

            if (rowsUpdatedCount > 1) {
                throw new DataStorageException("More than 1 event has been updated, despite having unique ID constraint!");
            }

            return entity;

        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update event: ", ex);
        }
    }

    @Override
    public void deleteById(UUID id) {
        var sql = "DELETE FROM Event WHERE id = ?";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, id.toString());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Event not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 event (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete event, id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Event";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all events", ex);
        }

    }

    @Override
    public boolean existsByGuid(UUID id) {
        var sql = """
                SELECT id
                FROM Event
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, id.toString());

            var resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to check whether event with id=%s exists!".formatted(id), ex);
        }

    }

    private static EventEntity eventFromResultSet(ResultSet resultSet) throws SQLException {
        return new EventEntity(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("category"),
                resultSet.getTimestamp("startDate").toLocalDateTime().toLocalDate(),
                resultSet.getTimestamp("startTime").toLocalDateTime().toLocalTime(),
                resultSet.getString("timeUnit"),
                resultSet.getInt("timeUnitCount"),
                resultSet.getString("description")
        );
    }
}
