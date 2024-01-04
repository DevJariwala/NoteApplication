package com.speer.notes.controllers.note;

import com.speer.notes.dtos.CreateNoteDTO;
import com.speer.notes.dtos.ShareNoteDTO;
import com.speer.notes.dtos.SuccessResponseDTO;
import com.speer.notes.dtos.UpdateContentDTO;
import com.speer.notes.models.Note;
import com.speer.notes.services.note.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/notes")
    public ResponseEntity<Note> createNote(@Valid @RequestBody CreateNoteDTO createNoteDTO) {
        Note note = noteService.createNote(createNoteDTO);
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @PutMapping("/notes/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable UUID noteId, @Valid @RequestBody UpdateContentDTO updateContentDTO) {
        Note note = noteService.updateNode(noteId, updateContentDTO);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getNotes() {
        List<Note> notes = noteService.getNotes();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/notes/{noteId}")
    public ResponseEntity<Note> getNote(@PathVariable UUID noteId) {
        Note note = noteService.getNote(noteId);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<SuccessResponseDTO> deleteNote(@PathVariable UUID noteId) {
        noteService.deleteNote(noteId);
        SuccessResponseDTO successResponseDTO = SuccessResponseDTO.builder()
                .message("Note is deleted successfully!!")
                .build();
        return new ResponseEntity<>(successResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/notes/{noteId}/share")
    public ResponseEntity<SuccessResponseDTO> shareNote(@PathVariable UUID noteId, @Valid @RequestBody ShareNoteDTO shareNoteDTO) {
        noteService.share(noteId, shareNoteDTO);
        SuccessResponseDTO successResponseDTO = SuccessResponseDTO.builder()
                .message("Note is shared successfully!!")
                .build();
        return new ResponseEntity<>(successResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Note>> searchNotes(@RequestParam String q) {
        List<Note> notes = noteService.search(q);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

}
