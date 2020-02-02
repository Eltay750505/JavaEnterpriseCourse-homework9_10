package ru.mail.romanov1234567890987.repository;

import ru.mail.romanov1234567890987.repository.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends GeneralRepository<User>{
    List<User> findAll(Connection connection) throws SQLException;
}
