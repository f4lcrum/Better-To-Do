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
import java.util.function.Supplier;

public class CategoryDao implements DataAccessObject<CategoryEntity> {

    private final Supplier<ConnectionHandler> connections;

    public CategoryDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public CategoryEntity create(CategoryEntity newCategory) {
        var sql = "INSERT INTO Category (id, name, color) VALUES (?, ?, ?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newCategory.id());
            statement.setString(2, newCategory.name());
            statement.setInt(3, newCategory.color());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                int categoryId;

                if (keyResultSet.next()) {
                    categoryId = keyResultSet.getInt(1);
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
                       color
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
            throw new DataStorageException("Failed to load all departments", ex);
        }
    }

    @Override
    public Optional<CategoryEntity> findById(String id) {
        var sql = """
                SELECT id,
                       name,
                       color
                FROM Category
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, id);

            var resultSet = statement.executeQuery();

            return resultSet.next() ? Optional.of(categoryFromResultSet(resultSet)) : Optional.empty();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load department by ID", ex);
        }
    }

    @Override
    public CategoryEntity update(CategoryEntity categoryEntity) {
        var sql = """
                UPDATE Category
                SET name = ?,
                    color = ?
                WHERE id = ?
                """;

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, categoryEntity.id());
            statement.setString(2, categoryEntity.name());
            statement.setInt(3, categoryEntity.color());
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

    private static CategoryEntity categoryFromResultSet(ResultSet resultSet) throws SQLException {
        return new CategoryEntity(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getInt("color")
        );
    }
}
