package cz.fi.muni.pv168.todo.business.entity;

/**
 * Provider of globally unique identifiers for new entities.o
 * The returned GUID should be globally unique. The provider will always
 * return a new identifier, which has not been used yet.
 *
 * @author Vojtech Sassmann
 */
public interface GuidProvider {

    String newGuid();
}