package com.speer.notes.services.note;

import com.speer.notes.dtos.CreateNoteDTO;
import com.speer.notes.dtos.ShareNoteDTO;
import com.speer.notes.dtos.UpdateContentDTO;
import com.speer.notes.exception.ResourceNotFoundException;
import com.speer.notes.exception.UnauthorizedActionException;
import com.speer.notes.models.Note;
import com.speer.notes.models.User;
import com.speer.notes.repositories.note.NoteRepository;
import com.speer.notes.services.user.UserService;
import com.speer.notes.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Note createNote(CreateNoteDTO createNoteDTO) {

        String currentUserEmail = Utils.getCurrentUserEmail();
        User user = userService.getUserByEmail(currentUserEmail);

        Note note = Note.builder()
                .content(createNoteDTO.getContent())
                .user(user)
                .author(currentUserEmail)
                .build();
        return noteRepository.save(note);
    }

    @Override
    @Transactional
    public Note updateNode(UUID noteId, UpdateContentDTO updateContentDTO) {

        String currentUserEmail = Utils.getCurrentUserEmail();

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        if (note.isDeleted()) throw new ResourceNotFoundException("Note not found");

        if (!currentUserEmail.equals(note.getAuthor())) {
            throw new UnauthorizedActionException("Unauthorized action");
        }

        Note updatedNote = note.toBuilder()
                .content(updateContentDTO.getContent())
                .build();

        return noteRepository.save(updatedNote);
    }

    @Override
    public List<Note> getNotes() {
        User user = userService.getUserByEmail(Utils.getCurrentUserEmail());
        return user.getNotes().stream().filter(note -> !note.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public Note getNote(UUID noteId) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        if (note.isDeleted()) throw new ResourceNotFoundException("Note not found");

        if (!Utils.getCurrentUserEmail().equals(note.getAuthor())) {
            throw new UnauthorizedActionException("Unauthorized action");
        }

        return note;
    }

    @Override
    @Transactional
    public void deleteNote(UUID noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        if (note.isDeleted()) throw new ResourceNotFoundException("Note not found");

        if (!Utils.getCurrentUserEmail().equals(note.getAuthor())) {
            throw new UnauthorizedActionException("Unauthorized action");
        }
        noteRepository.deleteById(noteId);
    }

    @Override
    @Transactional
    public void share(UUID noteId, ShareNoteDTO shareNoteDTO) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        if (note.isDeleted()) throw new ResourceNotFoundException("Note not found");

        if (!Utils.getCurrentUserEmail().equals(note.getAuthor())) {
            throw new UnauthorizedActionException("Unauthorized action");
        }

        User user = userService.getUserByEmail(shareNoteDTO.getEmail());
        note.setUser(user);
        noteRepository.save(note);

    }

    @Override
    public List<Note> search(String query) {
        return noteRepository.findByContentContainingIgnoreCase(query);
    }

}
