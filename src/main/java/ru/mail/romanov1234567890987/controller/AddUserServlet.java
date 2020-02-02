package ru.mail.romanov1234567890987.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.romanov1234567890987.controller.exception.IncorrectStringLengthException;
import ru.mail.romanov1234567890987.controller.exception.NumberCountException;
import ru.mail.romanov1234567890987.service.UserService;
import ru.mail.romanov1234567890987.service.impl.UserServiceImpl;
import ru.mail.romanov1234567890987.service.model.UserDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class AddUserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String PAGES = "/WEB-INF/pages";
    private static final int MAX_AGE = 120;
    private static final int MAX_STRING_LENGTH = 40;
    private static final int MAX_ADDRESS_LENGTH = 100;
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDTO userDTO = getUserDTOFromQuery(req);
            userService.add(userDTO);
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(PAGES + "/addUser.jsp");
            requestDispatcher.forward(req, resp);
        } catch (IncorrectStringLengthException e) {
            logger.error(e.getMessage(), e);
        } catch (NumberCountException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private UserDTO getUserDTOFromQuery(HttpServletRequest req) throws IncorrectStringLengthException, NumberCountException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String age = req.getParameter("age");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");
        Integer ageInteger = Integer.parseInt(age);
        String active = req.getParameter("active");
        boolean activeBoolean = Boolean.parseBoolean(active);
        if (userName.length() > MAX_STRING_LENGTH) {
            throw new IncorrectStringLengthException(
                    "The User name can't contain more than " + MAX_STRING_LENGTH + "symbols!!!"
            );
        } else if (password.length() > MAX_STRING_LENGTH) {
            throw new IncorrectStringLengthException(
                    "The password can't contain more than " + MAX_STRING_LENGTH + " symbols!!!"
            );
        } else if (address.length() > MAX_ADDRESS_LENGTH) {
            throw new IncorrectStringLengthException(
                    "The address can't contain more than" + MAX_ADDRESS_LENGTH + "symbols!!!"
            );
        } else if (telephone.length() > MAX_STRING_LENGTH) {
            throw new IncorrectStringLengthException(
                    "The telephone can't contain more than " + MAX_STRING_LENGTH + " symbols!!!"
            );
        } else if (ageInteger > MAX_AGE) {
            throw new NumberCountException("The users age cannot be greater than " + MAX_AGE + " !!!");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setActive(activeBoolean);
        userDTO.setUserName(userName);
        userDTO.setPassword(password);
        userDTO.setAddress(address);
        userDTO.setTelephone(telephone);
        userDTO.setAge(ageInteger);
        return userDTO;
    }
}
