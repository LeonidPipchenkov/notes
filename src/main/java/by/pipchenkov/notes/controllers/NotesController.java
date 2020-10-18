package by.pipchenkov.notes.controllers;

import by.pipchenkov.notes.models.Note;
import by.pipchenkov.notes.repo.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    @GetMapping("/notes")
    public String notesMain(Model model) {
        Iterable<Note> notes = notesRepository.findAll();
        model.addAttribute("notes", notes);
        return "notes-main";
    }

    @GetMapping("/notes/add")
    public String noteAdd(Model model) {
        return "notes-add";
    }

    @PostMapping("/notes/add")
    public String newNoteAdd(@RequestParam String title, @RequestParam String full_text, Model model) {
        Note note = new Note(title, full_text);
        notesRepository.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/notes/{id}")
    public String noteDetails(@PathVariable(value = "id") long id, Model model) {
        if (!notesRepository.existsById(id)) {
            return "redirect:/notes";
        }
        Optional<Note> note = notesRepository.findById(id);
        ArrayList<Note> res = new ArrayList<>();
        note.ifPresent(res::add);
        model.addAttribute("note", res);
        return "notes-details";
    }

    @GetMapping("/notes/{id}/edit")
    public String noteEdit(@PathVariable(value = "id") long id, Model model) {
        if (!notesRepository.existsById(id)) {
            return "redirect:/notes";
        }
        Optional<Note> note = notesRepository.findById(id);
        ArrayList<Note> res = new ArrayList<>();
        note.ifPresent(res::add);
        model.addAttribute("note", res);
        return "notes-edit";
    }

    @PostMapping("/notes/{id}/edit")
    public String newNoteUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String full_text, Model model) {
        Note note = notesRepository.findById(id).orElseThrow();
        note.setTitle(title);
        note.setFull_text(full_text);
        notesRepository.save(note);
        return "redirect:/notes";
    }

    @PostMapping("/notes/{id}/remove")
    public String noteDelete(@PathVariable(value = "id") long id,  Model model) {
        Note note = notesRepository.findById(id).orElseThrow();
        notesRepository.delete(note);
        return "redirect:/notes";
    }
}
