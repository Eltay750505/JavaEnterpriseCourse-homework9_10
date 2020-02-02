package ru.mail.romanov1234567890987.repository;

import ru.mail.romanov1234567890987.repository.model.UserInformation;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserInformationRepository extends GeneralRepository<UserInformation> {
    UserInformation update(Connection connection, UserInformation userInformation) throws SQLException;
}
