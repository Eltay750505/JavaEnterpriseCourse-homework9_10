package ru.mail.romanov1234567890987.controller;

import ru.mail.romanov1234567890987.service.TableService;
import ru.mail.romanov1234567890987.service.UserService;
import ru.mail.romanov1234567890987.service.impl.TableServiceImpl;
import ru.mail.romanov1234567890987.service.impl.UserServiceImpl;
import ru.mail.romanov1234567890987.service.model.UserDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AllUsersServlet extends HttpServlet {
    private static final String PAGES = "/WEB-INF/pages";
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> users = userService.findAll();
        req.setAttribute("users", users);

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(PAGES + "/users.jsp");

        requestDispatcher.forward(req, resp);
    }
}
