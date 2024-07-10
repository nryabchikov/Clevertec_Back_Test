package ru.clevertec.check.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.dto.CheckRequest;
import ru.clevertec.check.dto.ProductRequest;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.repository.CheckRepository;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.repository.DiscountCardRepository;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.util.CsvWriter;
import ru.clevertec.check.util.DatabaseProperties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/check")
public class CheckServlet extends HttpServlet {

    private final ProductService productService = new ProductService(new ProductRepository());
    private final DiscountCardService discountCardService = new DiscountCardService(new DiscountCardRepository());
    private final CheckRepository checkRepository = new CheckRepository(productService);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        DatabaseProperties.initDB();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            CheckRequest checkRequest = objectMapper.readValue(req.getReader(), CheckRequest.class);
            Map<Integer, Integer> mapOfIdAndAmountOfProduct = new HashMap<>();
            for (ProductRequest productRequest : checkRequest.getProducts()) {
                mapOfIdAndAmountOfProduct.merge(productRequest.getId(), productRequest.getQuantity(), Integer::sum);
            }
            int discountPercentage = discountCardService.getDiscountPercentageByNumber(checkRequest.getDiscountCard());
            Check check = checkRepository.generateCheck(mapOfIdAndAmountOfProduct, checkRequest.getDiscountCard(),
                    discountPercentage, checkRequest.getBalanceDebitCard());
            CsvWriter.writeCheckToCsv(check, "result.csv");
            File file = new File("result.csv");
            resp.setContentType("text/csv");
            resp.setHeader("Content-Disposition", "attachment; filename=check.csv");
            try (BufferedReader reader = new BufferedReader(new FileReader(file));
                 PrintWriter writer = resp.getWriter()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.println(line);
                }
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid input data");
        }
    }
}
