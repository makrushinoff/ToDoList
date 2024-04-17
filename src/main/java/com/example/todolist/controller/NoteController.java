package com.example.todolist.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.entity.Note;
import com.example.todolist.exception.NotFoundException;
import com.example.todolist.repository.NoteRepository;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteRepository noteRepository;

    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can not find note with such id"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNote(@RequestBody Note note) {
        noteRepository.save(note);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note updateNote(@PathVariable Long id, @RequestBody Note note) {
        if(!noteRepository.existsById(id)) {
            throw new NotFoundException("Can not find note with such id");
        }
        return noteRepository.save(note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNoteById(@PathVariable Long id) {
        if(!noteRepository.existsById(id)) {
            throw new NotFoundException("Can not find note with such id");
        }
        noteRepository.deleteById(id);
    }

}
