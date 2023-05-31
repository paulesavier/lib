package ru.itgirl.libraryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.entity.Author;
import ru.itgirl.libraryproject.entity.Book;
import ru.itgirl.libraryproject.entity.Genre;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookCreateDto) {
        Book book = bookRepository.save(convertToEntity(bookCreateDto));
        BookDto bookDto = convertToDto(book);
        return bookDto;
    }

    private Book convertToEntity(BookDto bookCreateDto) {
        Set<Author> authorSet = null;
        if (bookCreateDto.getAuthors() != null) {
            authorSet = bookCreateDto.getAuthors()
                    .stream()
                    .map(author -> Author.builder()
                            .name(author.getName())
                            .surname(author.getSurname())
                            .build())
                    .collect(Collectors.toSet());
        }
        Genre genre = genreRepository.findGenreByName(bookCreateDto.getGenre()).orElseThrow();
        Book book = Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .authors(authorSet)
                .build();
        return book;
    }

    private BookDto convertToDto(Book book) {
        List<AuthorDto> authorDtoList = null;
        if (book.getAuthors() != null) {
            authorDtoList = book.getAuthors()
                    .stream()
                    .map(author -> AuthorDto.builder()
                            .name(author.getName())
                            .surname(author.getSurname())
                            .build())
                    .toList();
        }

        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .authors(authorDtoList)
                .build();
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookUpdateDto) {
        Genre genre = genreRepository.findByName(bookUpdateDto.getGenre()).orElseThrow();
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        Set<Author> authorSet = null;
        if (bookUpdateDto.getAuthors() != null) {
            for (AuthorDto authorDto : bookUpdateDto.getAuthors()) {
                {
                    authorSet = bookUpdateDto.getAuthors()
                            .stream()
                            .map(author -> Author.builder()
                                    .name(author.getName())
                                    .surname(author.getSurname())
                                    .build())
                            .collect(Collectors.toSet());
                }
            }
        }
        book.setName(bookUpdateDto.getName());
        book.setAuthors(authorSet);
        book.setGenre(genre);
        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertToDto(savedBook);
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
