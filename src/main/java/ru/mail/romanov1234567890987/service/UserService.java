package ru.mail.romanov1234567890987.service;

import ru.mail.romanov1234567890987.service.model.UpdateUserDTO;
import ru.mail.romanov1234567890987.service.model.UserDTO;

import java.util.List;

public interface UserService {


    UserDTO add(UserDTO userDTO);

    UpdateUserDTO update(UpdateUserDTO updateUserDTO);

    List<UserDTO> findAll();

    void deleteUserById(Integer idInt);
}
