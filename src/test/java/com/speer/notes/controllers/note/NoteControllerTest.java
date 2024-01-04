package com.speer.notes.controllers.note;

import com.speer.notes.dtos.CreateNoteDTO;
import com.speer.notes.dtos.ShareNoteDTO;
import com.speer.notes.dtos.SuccessResponseDTO;
import com.speer.notes.dtos.UpdateContentDTO;
import com.speer.notes.models.Note;
import com.speer.notes.services.note.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @Test
    void testCreateNote() {
        CreateNoteDTO createNoteDTO = new CreateNoteDTO();
        createNoteDTO.setContent("Testing");

        Note mockNote = new Note();  // Add necessary data for the mockNote
        mockNote.setContent("Testing");
        mockNote.setAuthor("dev");
        mockNote.setNoteId(UUID.randomUUID());

        when(noteService.createNote(any(CreateNoteDTO.class))).thenReturn(mockNote);

        ResponseEntity<Note> responseEntity = noteController.createNote(createNoteDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockNote, responseEntity.getBody());
    }

    @Test
    void testUpdateNote() {
        UUID noteId = UUID.randomUUID();
        UpdateContentDTO updateContentDTO = new UpdateContentDTO();
        updateContentDTO.setContent("Updating");

        Note mockNote = new Note();  // Add necessary data for the mockNote
        mockNote.setContent("Updating");
        mockNote.setAuthor("dev");

        when(noteService.updateNode(any(UUID.class), any(UpdateContentDTO.class))).thenReturn(mockNote);

        ResponseEntity<Note> responseEntity = noteController.updateNote(noteId, updateContentDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockNote, responseEntity.getBody());
    }

    @Test
    void testGetNotes() {
        String query = "note";
        Note note1 = new Note();
        note1.setNoteId(UUID.randomUUID());
        note1.setAuthor("dev");
        note1.setContent("note1");

        Note note2 = new Note();
        note2.setNoteId(UUID.randomUUID());
        note2.setAuthor("rishabh");
        note2.setContent("note2");

        List<Note> mockNotes = Arrays.asList(note1, note2);

        when(noteService.getNotes()).thenReturn(mockNotes);

        ResponseEntity<List<Note>> responseEntity = noteController.getNotes();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockNotes, responseEntity.getBody());
    }

    @Test
    void testGetNote() {
        UUID noteId = UUID.randomUUID();
        Note mockNote = new Note();
        mockNote.setNoteId(noteId);
        mockNote.setContent("Testing");
        mockNote.setAuthor("dev");

        when(noteService.getNote(any(UUID.class))).thenReturn(mockNote);

        ResponseEntity<Note> responseEntity = noteController.getNote(noteId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockNote, responseEntity.getBody());
    }

    @Test
    void testDeleteNote() {
        UUID noteId = UUID.randomUUID();
        SuccessResponseDTO mockSuccessResponseDTO = SuccessResponseDTO.builder()
                .message("Note is deleted successfully!!")
                .build();

        doNothing().when(noteService).deleteNote(any(UUID.class));

        ResponseEntity<SuccessResponseDTO> responseEntity = noteController.deleteNote(noteId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testShareNote() {
        UUID noteId = UUID.randomUUID();
        ShareNoteDTO shareNoteDTO = new ShareNoteDTO();  // Add necessary data for shareNoteDTO
        SuccessResponseDTO mockSuccessResponseDTO = SuccessResponseDTO.builder()
                .message("Note is shared successfully!!")
                .build();

        doNothing().when(noteService).share(any(UUID.class), any(ShareNoteDTO.class));

        ResponseEntity<SuccessResponseDTO> responseEntity = noteController.shareNote(noteId, shareNoteDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testSearchNotes() {
        String query = "note";
        Note note1 = new Note();
        note1.setNoteId(UUID.randomUUID());
        note1.setAuthor("dev");
        note1.setContent("note1");

        Note note2 = new Note();
        note2.setNoteId(UUID.randomUUID());
        note2.setAuthor("rishabh");
        note2.setContent("note2");

        List<Note> mockNotes = Arrays.asList(note1, note2);

        when(noteService.search(anyString())).thenReturn(mockNotes);

        ResponseEntity<List<Note>> responseEntity = noteController.searchNotes(query);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockNotes, responseEntity.getBody());
    }
}
