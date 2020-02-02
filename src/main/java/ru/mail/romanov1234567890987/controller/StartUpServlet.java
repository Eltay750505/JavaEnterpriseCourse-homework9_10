package ru.mail.romanov1234567890987.controller;

import ru.mail.romanov1234567890987.service.TableService;
import ru.mail.romanov1234567890987.service.impl.TableServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class StartUpServlet extends HttpServlet {
    private TableService tableService = TableServiceImpl.getInstance();

    @Override
    public void init() throws ServletException {
        tableService.dropTablesIfExists();
        tableService.createTables();
    }

}
