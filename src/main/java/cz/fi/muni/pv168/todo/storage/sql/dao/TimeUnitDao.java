package cz.fi.muni.pv168.todo.storage.sql.dao;

import cz.fi.muni.pv168.todo.storage.sql.db.ConnectionHandler;
import cz.fi.muni.pv168.todo.storage.sql.entity.TimeUnitEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public final class TimeUnitDao implements DataAccessObject<TimeUnitEntity> {

    private final Supplier<ConnectionHandler> connections;

    public TimeUnitDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public TimeUnitEntity create(TimeUnitEntity newTimeUnit) {
        var sql = """
                INSERT INTO TimeUnit(
                    id,
                    name,
                    hourCount,
                    minuteCount
                )
                VALUES (?, ?, ?, ?);
                """;
        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newTimeUnit.id());
            statement.setString(2, newTimeUnit.name());
            statement.setLong(3, newTimeUnit.hourCount());
            statement.setLong(4, newTimeUnit.minuteCount());

            statement.executeUpdate();

            try (var keyResultSet = statement.getGeneratedKeys()) {
                UUID timeUnitId;

                if (keyResultSet.next()) {
                    timeUnitId = UUID.fromString(keyResultSet.getString(1));
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newTimeUnit);
                }

                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newTimeUnit);
                }

                return findById(timeUnitId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newTimeUnit, ex);
        }

    }

    @Override
    public Collection<TimeUnitEntity> findAll() {
        var sql = """
                SELECT id,
                       name,
                       hourCount,
                       minuteCount
                FROM TimeUnit
                """;
        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            List<TimeUnitEntity> timeUnits = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var timeUnit = timeUnitFromResultSet(resultSet);
                    timeUnits.add(timeUnit);
                }
            }

            return timeUnits;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all time units", ex);
        }

    }

    @Override
    public Optional<TimeUnitEntity> findById(UUID id) {
        var sql = """
                SELECT id,
                       name,
                       hourCount,
                       minuteCount
                FROM TimeUnit
                WHERE id = ?
                """;
        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, id.toString());

            var resultSet = statement.executeQuery();

            return resultSet.next() ?
                    Optional.of(timeUnitFromResultSet(resultSet)) :
                    Optional.empty();

        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load time unit by ID", ex);
        }
    }

    @Override
    public TimeUnitEntity update(TimeUnitEntity entity) {
        var sql = """
                UPDATE TimeUnit
                SET name = ?,
                    hourCount = ?,
                    minuteCount = ?
                WHERE id = ?
                """;
        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.name());
            statement.setLong(2, entity.hourCount());
            statement.setLong(3, entity.minuteCount());
            statement.setString(4, entity.id());

            var rowsUpdatedCount = statement.executeUpdate();

            if (rowsUpdatedCount == 0) {
                throw new DataStorageException("Time unit not found, seeked ID: " + entity.id());
            }

            if (rowsUpdatedCount > 1) {
                throw new DataStorageException("More than 1 time unit has been updated, despite having unique ID constraint!");
            }

            return entity;

        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update time unit: ", ex);
        }
    }

    @Override
    public void deleteById(UUID id) {
        var sql = "DELETE FROM TimeUnit WHERE id = ?";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, id.toString());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Time unit not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 time unit (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete time unit, id: " + id, ex);
        }

    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM TimeUnit";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all time units", ex);
        }
    }


    @Override
    public boolean existsByGuid(UUID id) {
        var sql = """
                SELECT id
                FROM TimeUnit
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
            throw new DataStorageException("Failed to check whether time unit with id=%s exists!".formatted(id), ex);
        }
    }

    private static TimeUnitEntity timeUnitFromResultSet(ResultSet resultSet) throws SQLException {
        return new TimeUnitEntity(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getLong("hourCount"),
                resultSet.getLong("minuteCount")
        );
    }

}
