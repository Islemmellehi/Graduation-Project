package com.example.vic.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/CLIENT")
public class ClientController {






        @Autowired

        ClientService clientService;

        private AtomicLong clientID = new AtomicLong();
        private final JdbcTemplate jdbcTemplate;

        @Autowired
        public ClientController(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }






        @CrossOrigin("*")
        @PostMapping("/addClient")
        public String addClient(
                @RequestParam("wfnamec") String name,
                @RequestParam("lastname") String lastname,
                @RequestParam("email") String email,
                @RequestParam("status") String status,
                @RequestParam("nbenf") int nbenf,
                @RequestParam("nbbeb") int nbbeb,
                @RequestParam("tely") String tel,
                @RequestParam("pass") String password
        ) {
            long idC = clientID.incrementAndGet();


            String selectQuery = "SELECT COUNT(*) FROM CLIENT WHERE ID_CLIENT = ?";
            int count = jdbcTemplate.queryForObject(selectQuery, Integer.class, idC);

            if (count > 0) {

                idC = clientID.incrementAndGet();


                count = jdbcTemplate.queryForObject(selectQuery, Integer.class, idC);

                if (count > 0) {

                    do {
                        idC = clientID.incrementAndGet();
                        count = jdbcTemplate.queryForObject(selectQuery, Integer.class, idC);
                    } while (count > 0);
                }
            }

            String insertQuery = "INSERT INTO CLIENT (ID_CLIENT, FIRST_NAME, LAST_NAME, MAIL_CLIENT, SF_CLIENT, NB_ENF, NB_BEBE, TEL_CLIENT, PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertQuery, idC, name, lastname, email, status, nbenf, nbbeb, tel, password);

            return "Saved client";
        }

        @CrossOrigin("*")
        @GetMapping("/loginClient")
        public ResponseEntity<String> loginClient(
                @RequestParam("wmailc") String email,
                @RequestParam("wpassc") String password
        ) {
            // Check if the email and password match a client in the database
            String selectQuery = "SELECT PASSWORD FROM CLIENT WHERE MAIL_CLIENT = ?";
            String savedPassword = jdbcTemplate.queryForObject(selectQuery, String.class, email);

            if (savedPassword != null && savedPassword.equals(password)) {

                // Client login successful
                return ResponseEntity.ok("Client login successful");
            } else {
                // Client login failed
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        }


     }



