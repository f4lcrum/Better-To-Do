package cz.fi.muni.pv168.todo.storage.sql.dao;

import cz.fi.muni.pv168.todo.storage.sql.db.ConnectionHandler;
import cz.fi.muni.pv168.todo.storage.sql.entity.CategoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class CategoryDao implements DataAccessObject<CategoryEntity> {

    private final Supplier<ConnectionHandler> connections;

    public CategoryDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public CategoryEntity create(CategoryEntity newCategory) {
        var sql = "INSERT INTO Category (id, name, r, g, b) VALUES (?, ?, ?, ?, ?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newCategory.id());
            statement.setString(2, newCategory.name());
            statement.setInt(3, newCategory.r());
            statement.setInt(4, newCategory.g());
            statement.setInt(5, newCategory.b());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                UUID categoryId;

                if (keyResultSet.next()) {
                    categoryId = UUID.fromString(keyResultSet.getString(1));
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newCategory);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newCategory);
                }

                return findById(categoryId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newCategory, ex);
        }
    }

    @Override
    public Collection<CategoryEntity> findAll() {
        var sql = """
                SELECT id,
                       name,
                       r,
                       g,
                       b
                FROM Category
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            List<CategoryEntity> categories = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var category = categoryFromResultSet(resultSet);
                    categories.add(category);

                }
            }

            return categories;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all categories", ex);
        }
    }

    @Override
    public Optional<CategoryEntity> findById(UUID id) {
        var sql = """
                SELECT id,
                       name,
                       r,
                       g,
                       b
                FROM Category
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, id.toString());

            var resultSet = statement.executeQuery();

            return resultSet.next() ? Optional.of(categoryFromResultSet(resultSet)) : Optional.empty();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load category by ID", ex);
        }
    }

    @Override
    public CategoryEntity update(CategoryEntity categoryEntity) {
        var sql = """
                UPDATE Category
                SET name = ?,
                    r = ?,
                    g = ?,
                    b = ?
                WHERE id = ?
                """;

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, categoryEntity.name());
            statement.setInt(2, categoryEntity.r());
            statement.setInt(3, categoryEntity.g());
            statement.setInt(4, categoryEntity.b());
            statement.setString(5, categoryEntity.id());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Category not found, id: " + categoryEntity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 category (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, categoryEntity));
            }
            return categoryEntity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update category: " + categoryEntity, ex);
        }
    }

    @Override
    public void deleteById(UUID id) {
        var sql = "DELETE FROM Category WHERE id = ?";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, id.toString());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Category not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More than 1 category (rows=%d) has been deleted: %s".formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete category,  id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Category";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all categories", ex);
        }
    }

    @Override
    public boolean existsByGuid(UUID id) {
        var sql = """
                SELECT id
                FROM Category
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
            throw new DataStorageException("Failed to check whether category with id=%s exists!".formatted(id), ex);
        }
    }

    private static CategoryEntity categoryFromResultSet(ResultSet resultSet) throws SQLException {
        return new CategoryEntity(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getInt("r"),
                resultSet.getInt("g"),
                resultSet.getInt("b")
        );
    }
}
