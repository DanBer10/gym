package com.gymapp.gym.notes;

import com.gymapp.gym.user.User;
import com.gymapp.gym.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotesService {
    @Autowired
    private UserService userService;
    @Autowired
    private NotesRepository repository;

    public PaginatedNotesResponse getAllNotesPaginated(HttpServletRequest request, Pageable pageable, String category) {
        final String email = request.getHeader("Email");
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found or not authenticated");
        }

        Page<Notes> notesPage;

        if (category != null && category.isEmpty()) {
            category = null;
        }

        notesPage = repository.findAllByUserIdAndCategory(user.getId(), category, pageable);

        long totalNotes = notesPage.getTotalElements();
        Page<NotesDto> notesDtoPage = notesPage.map(Notes::toDto);

        PaginatedNotesResponse response = new PaginatedNotesResponse();
        response.setNotes(notesDtoPage.getContent());
        response.setTotalNotes(totalNotes);

        return response;
    }



    public void createNotesForUser(int userId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            throw new RuntimeException("User was null when generating notes");
        }

        Notes notes = new Notes();
        notes.setUser(user);
        notes.setCreatedAt(Date.from(Instant.now()));
        notes.setTitle(null);
        notes.setContent(null);
        notes.setCategory(null);
        repository.save(notes);
    }


    public ResponseEntity<NotesDto> addNewNoteForUser(HttpServletRequest request, NotesDto data) {
        final String email = request.getHeader("Email");
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("No user was found");
        }

        Notes notes = new Notes();
        notes.setUser(user);
        notes.setCreatedAt(Date.from(Instant.now()));
        notes.setTitle(data.getTitle());
        notes.setContent(data.getContent());
        notes.setCategory(data.getCategory());

        repository.save(notes);

        NotesDto notesDto = NotesDto.builder().title(notes.getTitle()).content(notes.getContent()).build();

        return ResponseEntity.ok(notesDto);
    }
}
