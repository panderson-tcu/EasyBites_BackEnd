package edu.tcu.cs.easybites.system.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String objectName, Integer id) {
        super("Could not find " + objectName +" with ID " + id + ".");
    }

}
