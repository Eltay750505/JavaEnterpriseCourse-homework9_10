package ru.mail.romanov1234567890987.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.romanov1234567890987.service.UserService;
import ru.mail.romanov1234567890987.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class DeleteUserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String PAGES = "/WEB-INF/pages";
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            Integer idInt = Integer.parseInt(id);
            userService.deleteUserById(idInt);
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(PAGES + "/deleteUser.jsp");
            requestDispatcher.forward(req, resp);
        } catch (NumberFormatException e) {
            logger.error("Incorrect format", e);
        }
    }
}
