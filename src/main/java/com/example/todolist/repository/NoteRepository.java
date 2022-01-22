package com.example.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolist.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {}
