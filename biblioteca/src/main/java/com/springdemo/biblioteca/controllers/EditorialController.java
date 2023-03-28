package com.springdemo.biblioteca.controllers;

import com.springdemo.biblioteca.entities.Editorial;
import com.springdemo.biblioteca.exceptions.CRUDException;
import com.springdemo.biblioteca.services.EditorialService;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialService es;

    @GetMapping("/toRegister")
    public String getRegister() { return "editorial_createForm.html"; }

    @PostMapping("/toRegister")
    public String postRegister(@RequestParam String name, ModelMap model) {
        try {
            es.createEditorial(name);
            model.put("success", "Se creó la editorial con éxito.");
        }
        catch (CRUDException e) {
            System.err.println(e.getMessage());
            model.put("error", e.getMessage());
        }
        finally { return getRegister(); }
    }

    @GetMapping("/toModify")
    public String getModify(ModelMap model) { 
        List<Editorial> editorials = es.listEditorials();
        Collections.sort(editorials);
        model.addAttribute("editorials", editorials);
        return "editorial_modifyForm.html";
    }

    @PostMapping("/toModify")
    public String postModify(@RequestParam String oldName, @RequestParam String newName, ModelMap model) {
        try {
            es.modifyEditorial(oldName, newName);
            model.put("success", "Se modificó la editorial con éxito.");
        }
        catch (CRUDException e) {
            System.err.println(e.getMessage());
            model.put("error", e.getMessage());
        }
        finally { return getModify(model); }
    }

    @GetMapping("/toRemove")
    public String getRemove(ModelMap model) {
        List<Editorial> editorials = es.listEditorials();
        Collections.sort(editorials);
        model.addAttribute("editorials", editorials);
        return "editorial_removeForm.html"; 
    }

    @PostMapping("/toRemove")
    public String postRemove(@RequestParam String name, ModelMap model) {
        try {
            es.removeEditorial(name);
            model.put("success", "Se eliminó la editorial con éxito.");
        }
        catch (CRUDException e) {
            System.err.println(e.getMessage());
            model.put("error", e.getMessage());
        }
        finally { return getRemove(model); }
    }
    
    @GetMapping("/toConsult")
    public String getConsult(ModelMap model) {
        List<Editorial> editorials = es.listEditorials();
        // Buscar por nombre
        // Listar todos
        Collections.sort(editorials);
        model.addAttribute("editorials", editorials);
        return "editorial_consultTable.html"; 
    }

}
