package edu.tcu.cs.easybites.protein.utils;

public class ProteinNotFoundException extends RuntimeException {

    public ProteinNotFoundException(Integer proteinId) {
        super("Could not find protein with ID " + proteinId + ".");
    }
}
