package com.speer.notes.services.note;

import com.speer.notes.dtos.CreateNoteDTO;
import com.speer.notes.dtos.ShareNoteDTO;
import com.speer.notes.dtos.UpdateContentDTO;
import com.speer.notes.models.Note;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    Note createNote(CreateNoteDTO createNoteDTO);

    Note updateNode(UUID noteId, UpdateContentDTO updateContentDTO);

    List<Note> getNotes();

    Note getNote(UUID noteId);

    void deleteNote(UUID noteId);

    void share(UUID noteId, ShareNoteDTO shareNoteDTO);

    List<Note> search(String query);

}
