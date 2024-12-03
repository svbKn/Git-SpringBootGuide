package com.example.relationaldataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    @GetMapping
    public List<Customer> getAllCustomers() {
        return jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers",
                (rs, rowNum) -> new Customer(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                )
        );
    }







    @GetMapping("/Name")
    public List<Customer> getCustomersByFirstName(@RequestParam String firstName) {
        log.info("Executing query to fetch customers with firstName: {}", firstName);
        return jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
                new Object[]{firstName},
                (rs, rowNum) -> new Customer(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                )
        );
    }
}
