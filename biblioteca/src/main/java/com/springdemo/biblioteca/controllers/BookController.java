package com.springdemo.biblioteca.controllers;

import com.springdemo.biblioteca.entities.Author;
import com.springdemo.biblioteca.entities.Book;
import com.springdemo.biblioteca.entities.Editorial;
import com.springdemo.biblioteca.exceptions.CRUDException;
import com.springdemo.biblioteca.services.AuthorService;
import com.springdemo.biblioteca.services.BookService;
import com.springdemo.biblioteca.services.EditorialService;
import com.springdemo.biblioteca.utilities.SortBookByTitle;
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
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bs;
    @Autowired
    private AuthorService as;
    @Autowired
    private EditorialService es;

    @GetMapping("/toRegister")
    public String getRegister(ModelMap model) { 
        List<Author> authors = as.listAuthors();
        List<Editorial> editorials = es.listEditorials();
        Collections.sort(editorials);
        Collections.sort(authors);
        model.addAttribute("authors", authors);
        model.addAttribute("editorials", editorials);
        return "book_createForm.html";
    }

    @PostMapping("/toRegister")
    public String postRegister(@RequestParam(required = false) Long isbn,
                               @RequestParam String title,
                               @RequestParam(required = false) Integer exemplars,
                               @RequestParam String nameAuthor,
                               @RequestParam String nameEditorial,
                               ModelMap model) {
        try {
            bs.createBook(isbn, title, exemplars, nameAuthor, nameEditorial);
            model.put("success", "Se creó el libro correctamente.");
        }
        catch (CRUDException e) {
            System.err.println(e.getMessage());
            model.put("error", e.getMessage());
        }
        finally { return getRegister(model); }
    }

    @GetMapping("/toModify")
    public String getModify(ModelMap model) {
        List<Book> books = bs.listBooks();
        List<Author> authors = as.listAuthors();
        List<Editorial> editorials = es.listEditorials();
        Collections.sort(editorials);
        Collections.sort(authors);
        Collections.sort(books, new SortBookByTitle());
        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
        model.addAttribute("editorials", editorials);
        return "book_modifyForm.html";
    }

    @PostMapping("/toModify")
    public String postModify(@RequestParam(required = false) Long oldIsbn,
                             @RequestParam(required = false) Long newIsbn,
                             @RequestParam(required = false) String newTitle,
                             @RequestParam(required = false) Integer newExemplars,
                             @RequestParam(required = false) String newNameAuthor,
                             @RequestParam(required = false) String newNameEditorial,
                             ModelMap model) {
        try {
            bs.modifyBook(oldIsbn, newIsbn, newTitle, newExemplars, newNameAuthor, newNameEditorial);
            model.put("success", "Se modificó el libro correctamente.");
        }
        catch(CRUDException e) {
            System.err.println(e.getMessage());
            model.put("error", e.getMessage());
        }
        finally { return getModify(model); }
    }

    @GetMapping("/toRemove")
    public String getRemove(ModelMap model) { 
        List<Book> books = bs.listBooks();
        Collections.sort(books, new SortBookByTitle());
        model.addAttribute("books", books);
        return "book_removeForm.html";
    }

    @PostMapping("/toRemove")
    public String postRemove(@RequestParam Long isbn, ModelMap model) {
        try {
            bs.removeBook(isbn);
            model.put("success", "Se eliminó el libro con éxito.");
        }
        catch(CRUDException e) {
            System.err.println(e.getMessage());
            model.put("error", e.getMessage());
        }
        finally { return getRemove(model); }
    }

    @GetMapping("/toConsult")
    public String getConsult(ModelMap model) { 
        List<Book> books = bs.listBooks();
        // Buscar por ISBN
        // Buscar por titulo
        // Buscar por autor
        // Buscar por editorial
        // Listar todos
        Collections.sort(books, new SortBookByTitle());
        model.addAttribute("books", books);
        return "book_consultTable.html";
    }
    
}
