package cz.fi.muni.pv168.todo.io.mapper;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.io.entity.IoEntity;

public interface IoMapper<E extends Entity, M extends IoEntity> {

    M mapToIo(E entity);

    E mapToBusiness(M entity);
}
