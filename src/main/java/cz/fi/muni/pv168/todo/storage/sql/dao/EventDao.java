package cz.fi.muni.pv168.todo.storage.sql.dao;

import cz.fi.muni.pv168.todo.storage.sql.db.ConnectionHandler;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public final class EventDao implements DataAccessObject<EventEntity> {

    private final Supplier<ConnectionHandler> connections;

    public EventDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }
    @Override
    public EventEntity create(EventEntity entity) {
        return null;
    }

    @Override
    public Collection<EventEntity> findAll() {
        return null;
    }

    @Override
    public Optional<EventEntity> findById(long id) {
        return Optional.empty();
    }

    @Override
    public EventEntity update(EventEntity entity) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
