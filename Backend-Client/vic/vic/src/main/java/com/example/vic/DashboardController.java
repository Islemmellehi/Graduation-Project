package com.example.vic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class DashboardController {


    private AtomicLong LigResaCounter = new AtomicLong();
    private AtomicLong CodContrat = new AtomicLong();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DashboardController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //User sessions

    @CrossOrigin
    @GetMapping("/workroom")
    public ResponseEntity<List<Map<String, Object>>> getWorkRooms() {
        String request = "SELECT * FROM WORK_ROOM ";
        List<Map<String, Object>> workRooms = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(workRooms);
    }

    @CrossOrigin
    @GetMapping("/roomdetails")
    public ResponseEntity<List<Map<String, Object>>> getresRooms() {
        String request = "SELECT * FROM ROOM_CATEGORIE ";
        List<Map<String, Object>> workRooms = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(workRooms);
    }

    @CrossOrigin
    @GetMapping("/clearworkroom")
    public void clearRoom() {
        String request = "DELETE FROM  WORK_ROOM ";
        jdbcTemplate.queryForList(request);
    }

    @CrossOrigin
    @DeleteMapping("/deleteworkroom")
    public ResponseEntity<String> deleteWorkRoom(@RequestParam("workid") String workid) {
        String request = "DELETE FROM WORK_ROOM WHERE SESSION_ID = ?";
        jdbcTemplate.update(request, workid);
        return ResponseEntity.ok("Deleted successfully");

    }


    //Pensions

    @CrossOrigin
    @GetMapping("/pensions")
    public ResponseEntity<List<Map<String, Object>>> getPensions() {
        String request = "SELECT * FROM PENSION ";
        List<Map<String, Object>> pensions = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(pensions);
    }
    @CrossOrigin
    @GetMapping("/pensionadd")
    public ResponseEntity<String> addPension(
            @RequestParam("codP") String codP,
            @RequestParam("libP") String libP
                                             ) {
        String request = "INSERT INTO PENSION (COD_PENS,LIB_PENS) VALUES(?,?)";
        jdbcTemplate.update(request,codP,libP);
        return ResponseEntity.ok("Added Successfully");

    }


    //Messages
    @CrossOrigin
    @GetMapping("/messages")
    public ResponseEntity<List<Map<String, Object>>> getMessages() {
        String request = "SELECT * FROM MESSAGE ";
        List<Map<String, Object>> messages = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(messages);
    }

    //Hotels
    @CrossOrigin
    @GetMapping("/hotels")
    public ResponseEntity<List<Map<String, Object>>> getHotels() {
        String request = "SELECT * FROM HOTEL ";
        List<Map<String, Object>> hotel= jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(hotel);
    }

    //users
    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getUsers() {
        String request = "SELECT * FROM USERS ";
        List<Map<String, Object>> user= jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(user);
    }


    @CrossOrigin
    @GetMapping("/sendmessage")
    public ResponseEntity<String> sendMessage(
            @RequestParam("sender") String sender,
            @RequestParam("content") String content)

    {
        //contacting hotel
        String sql = "SELECT DBMS_RANDOM.STRING('U', 10) AS COD_MESSAGE FROM DUAL";
        String codMessage = jdbcTemplate.queryForObject(sql, String.class);

        String insertSql = "INSERT INTO MESSAGE (SENDER,CONTENT,COD_MESSAGE) VALUES(?,?,?)";
        jdbcTemplate.update(insertSql, sender,content,codMessage);

        return ResponseEntity.ok("Sent successfully");
    }


    @CrossOrigin
    @DeleteMapping("/deletemessage")
    public ResponseEntity<String> deleteMessage(@RequestParam("msg") String msg) {
        String request = "DELETE FROM MESSAGE WHERE COD_MESSAGE= ?";
        jdbcTemplate.update(request, msg);
        return ResponseEntity.ok("Deleted successfully");

    }
    @CrossOrigin
    @GetMapping("/clearmessages")
    public void clearMessages() {
        String request = "DELETE FROM  MESSAGE ";
        jdbcTemplate.queryForList(request);
    }

    @CrossOrigin
    @GetMapping("/updatemessage")
    public ResponseEntity<String> updateMessage(
            @RequestParam("msgid") String msgid,
            @RequestParam("sender") String sender,
            @RequestParam("content") String content)

             {
        // Delete the existing row with the given ID
        String deleteSql = "DELETE FROM MESSAGE WHERE COD_MESSAGE = ?";
        jdbcTemplate.update(deleteSql, msgid);

        // Insert the updated message with the new values
        String insertSql = "INSERT INTO MESSAGE (COD_MESSAGE, SENDER, CONTENT) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, msgid, sender, content);

        return ResponseEntity.ok("Updated successfully");
    }

    //Contrat


    @CrossOrigin

    @GetMapping("/contrats")
    public ResponseEntity<List<Map<String, Object>>> getContrats() {
        String request = "SELECT * FROM CONTRAT ";
        List<Map<String, Object>> contrats = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(contrats);
    }
    @CrossOrigin
    @GetMapping("/contratadd")
    public ResponseEntity<String> addContrat(
            @RequestParam("date_debC") Long date_debC,
            @RequestParam("date_finC") Long date_finC,
            @RequestParam("codC") String codC
    ) {
        // Convert the Unix timestamps to Java Timestamps if necessary
        Timestamp dateDebC = new Timestamp(date_debC * 1000);
        Timestamp dateFinC = new Timestamp(date_finC * 1000);

        String request = "INSERT INTO CONTRAT (COD_CONTRAT,DATE_DEBUT,DATE_FIN) VALUES(?,?,?)";
        jdbcTemplate.update(request, codC, dateDebC, dateFinC);
        return ResponseEntity.ok("Added Successfully");
    }
    @CrossOrigin
    @DeleteMapping("/deletecontrat")
    public ResponseEntity<String> deletecontrat(@RequestParam("cont") String cont) {
        String request = "DELETE FROM CONTRAT WHERE COD_CONTRAT= ?";
        jdbcTemplate.update(request, cont);
        return ResponseEntity.ok("Deleted successfully");

    }

    //Reservation
    @CrossOrigin
    @GetMapping("/res")
    public ResponseEntity<List<Map<String, Object>>> getReservations() {
        String request = "SELECT * FROM RESERVATION ";
        List<Map<String, Object>> reservations = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(reservations);
    }

    //Reservation details
    @CrossOrigin
    @GetMapping("/resdetails")
    public ResponseEntity<List<Map<String, Object>>> getReservationDetails() {
        String request = "SELECT * FROM RESERVATION_DETAIL ";
        List<Map<String, Object>> reservationsD = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(reservationsD);
    }

    //Room Categories
    @CrossOrigin
    @GetMapping("/rooms")
    public ResponseEntity<List<Map<String, Object>>> getRooms() {
        String request = "SELECT * FROM ROOM_CATEGORIE ";
        List<Map<String, Object>> room = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(room);
    }

    //Room Vues
    @CrossOrigin
    @GetMapping("/roomvues")
    public ResponseEntity<List<Map<String, Object>>> getRoomVues() {
        String request = "SELECT * FROM ROOM_VUE ";
        List<Map<String, Object>> roomv = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(roomv);
    }

    //Stock
    @CrossOrigin
    @GetMapping("/stock")
    public ResponseEntity<List<Map<String, Object>>> getRoomStock() {
        String request = "SELECT * FROM CONTRAT_STOCK_ROOM ";
        List<Map<String, Object>> roomS = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(roomS);
    }

    //contrat prix
    @CrossOrigin
    @GetMapping("/contratprix")
    public ResponseEntity<List<Map<String, Object>>> getPrix() {
        String request = "SELECT * FROM CONTRAT_PRIX ";
        List<Map<String, Object>> prix = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(prix);
    }

    //Clients
    @CrossOrigin
    @GetMapping("/clients")
    public ResponseEntity<List<Map<String, Object>>> getClients() {
        String request = "SELECT * FROM CLIENT ";
        List<Map<String, Object>> client = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(client);
    }

    //Contrat periodes
    @CrossOrigin
    @GetMapping("/contratperiodes")
    public ResponseEntity<List<Map<String, Object>>> getPeriodes() {
        String request = "SELECT * FROM CONTRAT_PERIODE ";
        List<Map<String, Object>> P = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(P);
    }











}
