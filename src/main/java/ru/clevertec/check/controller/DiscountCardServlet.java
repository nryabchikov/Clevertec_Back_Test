package ru.clevertec.check.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.repository.DiscountCardRepository;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.util.DatabaseProperties;
import ru.clevertec.check.util.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/discountcards")
public class DiscountCardServlet extends HttpServlet {

    private final DiscountCardService discountCardService = new DiscountCardService(new DiscountCardRepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        DatabaseProperties.initDB();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Discount card ID is required");
            return;
        }

        int id = Integer.parseInt(idParam);
        try {
            DiscountCard discountCard = discountCardService.getDiscountCardById(id);
            if (discountCard != null) {
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), discountCard);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Discount card not found");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DiscountCard discountCard;
        try {
            discountCard = JsonParser.parseDiscountCardFromRequest(req);
            discountCardService.addDiscountCard(discountCard);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Discount card added successfully");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid input data");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Discount card ID is required");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid discount card ID");
            return;
        }

        try {
            DiscountCard discountCard = JsonParser.parseDiscountCardFromRequest(req);
            discountCard.setId(id);
            discountCardService.updateDiscountCard(discountCard);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Discount card updated successfully");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid input data");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Discount card ID is required");
            return;
        }

        int id = Integer.parseInt(idParam);
        try {
            discountCardService.deleteDiscountCard(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Discount card deleted successfully");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }
}
