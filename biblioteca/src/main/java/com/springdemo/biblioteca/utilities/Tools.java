package com.springdemo.biblioteca.utilities;

import com.springdemo.biblioteca.exceptions.CRUDException;

public interface Tools {
    public default void validate(String name, Object value) throws CRUDException {

        if(value == null)
            throw new CRUDException(String.format("El %s no puede ser nulo.", name));
        else if(value instanceof String && ((String) value).isEmpty())
            throw new CRUDException(String.format("El %s no puede estar vacio.", name));

    }

}
