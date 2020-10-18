package by.pipchenkov.notes.repo;

import by.pipchenkov.notes.models.Note;
import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<Note, Long> {
}
