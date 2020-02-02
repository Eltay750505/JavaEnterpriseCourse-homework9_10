package ru.mail.romanov1234567890987.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.romanov1234567890987.repository.ConnectionRepository;
import ru.mail.romanov1234567890987.repository.UserInformationRepository;
import ru.mail.romanov1234567890987.repository.UserRepository;
import ru.mail.romanov1234567890987.repository.impl.ConnectionRepositoryImpl;
import ru.mail.romanov1234567890987.repository.impl.UserInformationRepositoryImpl;
import ru.mail.romanov1234567890987.repository.impl.UserRepositoryImpl;
import ru.mail.romanov1234567890987.repository.model.User;
import ru.mail.romanov1234567890987.repository.model.UserInformation;
import ru.mail.romanov1234567890987.service.UserService;
import ru.mail.romanov1234567890987.service.model.UpdateUserDTO;
import ru.mail.romanov1234567890987.service.model.UserDTO;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static UserService instance;
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private UserInformationRepository userInformationRepository = UserInformationRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User databaseUser = convertDTOToDatabaseUser(userDTO);
                User addedUser = userRepository.add(connection, databaseUser);
                databaseUser.getUserInformation().setUserId(addedUser.getId());
                userInformationRepository.add(connection, databaseUser.getUserInformation());
                connection.commit();
                return convertDatabaseUserToDTO(addedUser);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public UpdateUserDTO update(UpdateUserDTO updateUserDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                UserInformation databaseUserInformation = convertDTOToDatabaseUpdateUserInformation(updateUserDTO);
                UserInformation addedUpdateUser = userInformationRepository.update(connection, databaseUserInformation);
                connection.commit();
                return convertDatabaseUpdateUserToDTO(addedUpdateUser);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<UserDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<UserDTO> userDTOS = new ArrayList<>();
                List<User> users = userRepository.findAll(connection);
                for (User element : users) {
                    UserDTO userDTO = convertDatabaseUserToDTO(element);
                    userDTOS.add(userDTO);
                }
                connection.commit();
                return userDTOS;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void deleteUserById(Integer idInt) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userInformationRepository.deleteById(connection, idInt);
                userRepository.deleteById(connection, idInt);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private UserInformation convertDTOToDatabaseUpdateUserInformation(UpdateUserDTO updateUserDTO) {
        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(updateUserDTO.getAddress());
        userInformation.setUserId(updateUserDTO.getId());
        return userInformation;
    }

    private UpdateUserDTO convertDatabaseUpdateUserToDTO(UserInformation addedUpdateUser) {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setId(addedUpdateUser.getUserId());
        updateUserDTO.setAddress(addedUpdateUser.getAddress());
        return updateUserDTO;
    }

    private UserDTO convertDatabaseUserToDTO(User databaseUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setActive(databaseUser.getActive());
        userDTO.setId(databaseUser.getId());
        userDTO.setAge(databaseUser.getAge());
        userDTO.setUserName(databaseUser.getUsername());
        userDTO.setPassword(databaseUser.getPassword());
        userDTO.setAddress(databaseUser.getUserInformation().getAddress());
        userDTO.setTelephone(databaseUser.getUserInformation().getTelephone());
        return userDTO;
    }

    private User convertDTOToDatabaseUser(UserDTO userDTO) {
        User user = new User();
        user.setActive(userDTO.isActive());
        user.setAge(userDTO.getAge());
        user.setUsername(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(userDTO.getAddress());
        userInformation.setTelephone(userDTO.getTelephone());
        user.setUserInformation(userInformation);
        return user;
    }
}
