package ru.itgirl.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.libraryproject.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
