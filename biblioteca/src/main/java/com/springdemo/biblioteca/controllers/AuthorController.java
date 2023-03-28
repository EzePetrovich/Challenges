package com.springdemo.biblioteca.controllers;

import com.springdemo.biblioteca.entities.Author;
import com.springdemo.biblioteca.exceptions.CRUDException;
import com.springdemo.biblioteca.services.AuthorService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService as;

    @GetMapping("/toRegister")
    public String getRegister() { return "author_createForm.html"; }

    @PostMapping("/toRegister")
    public String postRegister(@RequestParam String name, ModelMap model) {
        try {
            as.createAuthor(name);
            model.put("success", "Se creó el autor correctamente.");
        }
        catch (CRUDException e) {
            System.err.println(e.getMessage());
            model.put("error", e.getMessage());
        }
        finally { return getRegister(); }
    }

    @GetMapping("/toModify")
    public String getModify(ModelMap model) {
        List<Author> authors = as.listAuthors();
        Collections.sort(authors);
        model.addAttribute("authors", authors);
        return "author_modifyForm.html";
    }

    @PostMapping("/toModify")
    public String postModify(@RequestParam String oldName, @RequestParam String newName, ModelMap model) {
        try {
            as.modifyAuthor(oldName, newName);
            model.put("success", "Se modificó el autor correctamente");
        }
        catch (CRUDException e) {
            System.err.println(e.getMessage());
            model.put("error", e.getMessage());
        }
        finally { return getModify(model); }
    }

    @GetMapping("/toRemove")
    public String getRemove(ModelMap model) {
        List<Author> authors = as.listAuthors();
        Collections.sort(authors);
        model.addAttribute("authors", authors);
        return "author_removeForm.html";
    }

    @PostMapping("/toRemove")
    public String postRemove(@RequestParam String name, ModelMap model) {
        try {
            as.removeAuthor(name);
            model.put("success", "Se eliminó el autor correctamente.");
        }
        catch(CRUDException e) {
            System.err.println(e.getMessage());
            model.put("error", e.getMessage());
        }
        finally { return getRemove(model); }
    }

    @GetMapping("/toConsult")
    public String getConsult(ModelMap model) {
        List<Author> authors = as.listAuthors();
        // Listar todos
        // Buscar por nombre
        Collections.sort(authors);
        model.addAttribute("authors", authors);
        return "author_consultTable.html";
    }
    
    

}
