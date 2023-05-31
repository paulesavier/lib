package ru.itgirl.libraryproject.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.libraryproject.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findGenreByName(String name);

    Optional<Genre> findByName(String name);
}