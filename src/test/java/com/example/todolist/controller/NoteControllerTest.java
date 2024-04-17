package com.example.todolist.controller;

import com.example.todolist.entity.Note;
import com.example.todolist.exception.NotFoundException;
import com.example.todolist.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    private static final Long ID = 1L;
    private static final String LABEL = "label";

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteController testInstance;

    @Test
    void shouldGetNoteById_success() {
        Note note = this.note();

        when(noteRepository.findById(ID))
                .thenReturn(Optional.of(note));

        Note actual = testInstance.getNoteById(ID);

        assertEquals(note, actual);
    }

    @Test
    void shouldGetNoteById_fail_notFoundById() {
        when(noteRepository.findById(ID))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> testInstance.getNoteById(ID));
    }

    @Test
    void shouldCreateNote() {
        Note note = this.note();

        testInstance.createNote(note);

        verify(noteRepository).save(note);
    }

    @Test
    void shouldUpdateNote_success() {
        Note note = this.note();

        when(noteRepository.existsById(ID))
                .thenReturn(true);
        when(noteRepository.save(note))
                .thenReturn(note);

        Note actual = testInstance.updateNote(ID, note);

        assertEquals(note, actual);
    }

    @Test
    void shouldUpdateNote_fail_notFoundById() {
        Note note = this.note();

        when(noteRepository.existsById(ID))
                .thenReturn(false);

        assertThrows(NotFoundException.class,
                () -> testInstance.updateNote(ID, note));

        verify(noteRepository, never()).save(any(Note.class));
    }

    @Test
    void shouldDeleteNoteById_success() {
        when(noteRepository.existsById(ID))
                .thenReturn(true);

        testInstance.deleteNoteById(ID);

        verify(noteRepository).deleteById(ID);
    }

    @Test
    void shouldDeleteNoteById_fail_notFoundById() {
        when(noteRepository.existsById(ID))
                .thenReturn(false);

        assertThrows(NotFoundException.class,
                () -> testInstance.deleteNoteById(ID));

        verify(noteRepository, never()).deleteById(ID);
    }

    private Note note() {
        return Note.builder()
                .done(true)
                .label(LABEL)
                .important(true)
                .build();
    }
}