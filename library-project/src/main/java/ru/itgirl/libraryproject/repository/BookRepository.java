package ru.itgirl.libraryproject.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.libraryproject.entity.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookById(Long id);
}