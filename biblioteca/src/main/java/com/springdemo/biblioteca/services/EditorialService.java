package com.springdemo.biblioteca.services;

import com.springdemo.biblioteca.entities.Author;
import com.springdemo.biblioteca.entities.Editorial;
import com.springdemo.biblioteca.exceptions.CRUDException;
import com.springdemo.biblioteca.repositories.EditorialRepository;
import com.springdemo.biblioteca.utilities.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialService implements Tools {

    @Autowired
    private EditorialRepository er;

    @Transactional
    public void createEditorial(String name) throws CRUDException {
        /* Create editorial */
        validate("nombre", name);
        Editorial editorial = new Editorial(name);
        editorial.setRemoved(Boolean.FALSE);
        editorial.setRemovedDate(null);
        /* Save editorial */
        Optional<Editorial> answerEditorial = er.findByName(editorial.getName());
        if(answerEditorial.isPresent()) {
            editorial = answerEditorial.get();
            if(editorial.getRemoved()) {
                editorial.setRemoved(Boolean.FALSE);
                editorial.setRemovedDate(null);
            }
            else { throw new CRUDException("La editorial ya existe."); }
        }
        er.save(editorial);
    }

    public List<Editorial> listEditorials() { return er.findAll(); }

    @Transactional
    public void modifyEditorial(String oldName, String newName) throws CRUDException {
        /* Find the editorial */
        validate("nombre", oldName);
        Optional<Editorial> answerEditorial = er.findByName(oldName);
        /* If find then modify */
        if(answerEditorial.isPresent()) {
            Editorial editorial = answerEditorial.get();
            validate("nombre", newName);
            editorial.setName(newName);
            er.save(editorial);
        }
        else { throw new CRUDException("La editorial no existe."); }
    }

    @Transactional
    public void removeEditorial(String name) throws CRUDException {
        /* Find the author */
        validate("nombre", name);
        Optional<Editorial> answerEditorial = er.findByName(name);
        /* If find then it's removed */
        if(answerEditorial.isPresent()) {
            Editorial editorial = answerEditorial.get();
            editorial.setRemoved(Boolean.TRUE);
            editorial.setRemovedDate(new Date());
            er.save(editorial);
        }
        else { throw new CRUDException("La editorial no existe."); }
    }

}
