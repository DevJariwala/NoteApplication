package com.speer.notes.repositories.note;

import com.speer.notes.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    List<Note> findByContentContainingIgnoreCase(String content);

    @Query("update Note n set n.deleted=true where n.id=?1")
    @Modifying
    void deleteById(UUID noteId);

}
