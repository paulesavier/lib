package ru.itgirl.libraryproject.service;
import ru.itgirl.libraryproject.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookCreateDto);
    BookDto updateBook(BookDto bookUpdateDto);
    void deleteBook(Long id);
    List<BookDto> getAllBooks();
}
