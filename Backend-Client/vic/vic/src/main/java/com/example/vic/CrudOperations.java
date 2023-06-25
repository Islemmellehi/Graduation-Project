/*package com.example.vic;
import java.sql.Timestamp;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class CrudOperations {
    private AtomicLong LigResaCounter = new AtomicLong();
    private AtomicLong CodContrat = new AtomicLong();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CrudOperations(JdbcTemplate jdbcTemplate) {
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
    @GetMapping("/Pensionadd")

    public ResponseEntity<String> Pensionadd(
            @RequestParam("cod") String codP,
            @RequestParam("lib") String libP
    ) {
        String request = "INSERT INTO PENSION (COD_PENS,LIB_PENS) VALUES(?,?)";
        jdbcTemplate.update(request,codP,libP);
        return ResponseEntity.ok("Added Successfully");

    }
    //DELETE PENSION
    @CrossOrigin
    @DeleteMapping("/deletepension")

    public ResponseEntity<String> deletepension(@RequestParam("pens") String codp) {
        String request = "DELETE FROM PENSION WHERE COD_PENS= ?";
        jdbcTemplate.update(request, codp);
        return ResponseEntity.ok("Deleted successfully");

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
    //UPDATE HOTELL
    @CrossOrigin
    @GetMapping("/updateHotel")
    public ResponseEntity<String> updateHotel(
            @RequestParam("codHotel") String codH,
            @RequestParam("lib") String lib,
            @RequestParam("abr") String abr,
            @RequestParam("pays") String pays,
            @RequestParam("vill") String vill,
            @RequestParam("classe") String classe,
            @RequestParam("catg") String catg
            )

    {
        // Delete the existing row with the given ID
        String deleteSql = "DELETE FROM HOTEL WHERE COD_HOTEL = ? ";
        jdbcTemplate.update(deleteSql, codH);

        // Insert the updated message with the new values
        String insertSql = "INSERT INTO HOTEL (COD_HOTEL, LIB_HOTEL, ABR_HOTEL, PAYS_HOTEL,VILL_HOTEL, CLASS_HOTEL,CATEG_HOTEL) VALUES (?, ?, ?, ?,?,?,?)";
        jdbcTemplate.update(insertSql,  codH, lib, abr, pays,vill, classe,catg);

        return ResponseEntity.ok("Updated successfully");
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
    //DELETE RES DETAILS AND RES
    @CrossOrigin
    @DeleteMapping("/deleteReservation")
    public ResponseEntity<String> deleteReservation(@RequestParam("codresa") String codresa,
                                              @RequestParam("ligresa") String ligresa

    ) {
        String deleteResDQuery = "DELETE FROM RESERVATION_DETAIL WHERE COD_RESA = ? AND LIG_RESA = ? ";
        jdbcTemplate.update(deleteResDQuery, codresa, ligresa);
        String deleteReserQuery = "DELETE FROM RESERVATION WHERE COD_RESA = ?";
        jdbcTemplate.update(deleteReserQuery, codresa);
        return ResponseEntity.ok("Deleted successfully");

    }
    //Room Categories
    @CrossOrigin
    @GetMapping("/rooms")
    public ResponseEntity<List<Map<String, Object>>> getRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int offset = page * size;
        String request = "SELECT * FROM ROOM_CATEGORIE LIMIT ?, ?";
        List<Map<String, Object>> room = jdbcTemplate.queryForList(request, offset, size);
        return ResponseEntity.ok(room);
    }
    //DELETE ROOM CATG
    @CrossOrigin
    @DeleteMapping("/deleteRoom")
    public ResponseEntity<String> deleteStock(@RequestParam("codR") String catroom


    ) {
        String request = "DELETE FROM ROOM_CATEGORIE WHERE CAT_ROOM = ? ";
        jdbcTemplate.update(request, catroom);
        return ResponseEntity.ok("Deleted successfully");

    }
    //ADD ROOOM CATG
    @CrossOrigin
    @GetMapping("/RoomAdd")
    public ResponseEntity<String> RoomAdd(
            @RequestParam("catroom") String category,
            @RequestParam("name") String lib,
            @RequestParam("view") String codvue,
            @RequestParam("beds") Integer nbbed,
            @RequestParam("large") String large,
            @RequestParam("ac") String ac,
            @RequestParam("shower") String shower,
            @RequestParam("tv") String tv,
            @RequestParam("bebcote") String bebcote,
            @RequestParam("chauf") String chauf,
            @RequestParam("kitchen") String kitchen,
            @RequestParam("balcony") String balcony,
            @RequestParam("wifi") String wifi,
            @RequestParam("tell") String tell,
            @RequestParam("nbpax")Integer nbpax,
            @RequestParam("nbadl") Integer nbadl,
            @RequestParam("nbenf") Integer nbenf,
            @RequestParam("nbbeb") Integer nbbeb

    ) {


        String request = "INSERT INTO ROOM_CATEGORIE (CAT_ROOM,LIB_ROMM,COD_VUE, NB_LIT, GRAND_LIT,CLIMATISEUR,BAIN,TV,BEBE_COTE, CHAUFFAGE,INTERNET, TELEPHONE,BALCON, KITCHEN,NB_PAX,NB_ADL,NB_ENF,NB_BEB) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(request, category,lib,codvue, nbbed,large,ac,shower,tv,bebcote,chauf,wifi,tell,balcony,kitchen,nbpax,nbadl,nbenf,nbbeb);
        return ResponseEntity.ok("Added Successfully");
    }

    //Room Vues
    @CrossOrigin
    @GetMapping("/roomvues")
    public ResponseEntity<List<Map<String, Object>>> getRoomVues() {
        String request = "SELECT * FROM ROOM_VUE ";
        List<Map<String, Object>> roomv = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(roomv);
    }
    //DELETE ROOM VIEWS
    @CrossOrigin
    @DeleteMapping("/deleteVue")
    public ResponseEntity<String> deleteStock(@RequestParam("codv") String codview,
                                              @RequestParam("libvue") String name

    ) {
        String request = "DELETE FROM ROOM_VUE WHERE COD_VUE = ? AND LIB_VUE = ? ";
        jdbcTemplate.update(request, codview, name);
        return ResponseEntity.ok("Deleted successfully");

    }
    //ADD VIEW
    @CrossOrigin
    @GetMapping("/ViewAdd")
    public ResponseEntity<String> ViewAdd(
            @RequestParam("codVue") String ID,
            @RequestParam("libV") String lib

    ) {


        String request = "INSERT INTO ROOM_VUE (COD_VUE,LIB_VUE) VALUES(?,?)";
        jdbcTemplate.update(request, ID,lib);
        return ResponseEntity.ok("Added Successfully");
    }
    //Stock
    @CrossOrigin
    @GetMapping("/stock")
    public ResponseEntity<List<Map<String, Object>>> getRoomStock() {
        String request = "SELECT * FROM CONTRAT_STOCK_ROOM ";
        List<Map<String, Object>> roomS = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(roomS);
    }
    //ADD STOCK
    @CrossOrigin
    @GetMapping("/Stockadd")
    public ResponseEntity<String> Stockadd(
            @RequestParam("room_catg") String category,
            @RequestParam("room_stock") Integer NBstock,
            @RequestParam("codC") String codC,
            @RequestParam("codP") String codP
    ) {


        String request = "INSERT INTO CONTRAT_STOCK_ROOM (COD_CONTRAT,COD_PERIODE,CAT_ROOM, STOCK) VALUES(?,?,?,?)";
        jdbcTemplate.update(request, codC,codP, category, NBstock);
        return ResponseEntity.ok("Added Successfully");
    }
    //DELETE STOCK
    @CrossOrigin
    @DeleteMapping("/deleteStock")
    public ResponseEntity<String> deleteStock(@RequestParam("cont") String codC,
                                              @RequestParam("periodID") String codP,
                                              @RequestParam("roomctg") String roomctg
    ) {
        String request = "DELETE FROM CONTRAT_STOCK_ROOM WHERE COD_CONTRAT = ? AND COD_PERIODE = ? AND CAT_ROOM = ?";
        jdbcTemplate.update(request, codC, codP, roomctg);
        return ResponseEntity.ok("Deleted successfully");

    }
    //UPDATE STOCK
    @CrossOrigin
    @GetMapping("/updateStock")
    public ResponseEntity<String> updateStock(
            @RequestParam("codcontrat") String codc,
            @RequestParam("codperiod") Integer codp,
            @RequestParam("roomcatg") String category,
            @RequestParam("nbstock") Integer stock)

    {
        // Delete the existing row with the given ID
        String deleteSql = "DELETE FROM CONTRAT_STOCK_ROOM WHERE COD_CONTRAT = ? AND COD_PERIODE = ? AND CAT_ROOM = ?";
        jdbcTemplate.update(deleteSql, codc, codp, category);

        // Insert the updated message with the new values
        String insertSql = "INSERT INTO CONTRAT_STOCK_ROOM (COD_CONTRAT, COD_PERIODE, CAT_ROOM, STOCK) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, codc, codp, category, stock);

        return ResponseEntity.ok("Updated successfully");
    }
    //contrat prix
    @CrossOrigin
    @GetMapping("/contratprix")
    public ResponseEntity<List<Map<String, Object>>> getPrix() {
        String request = "SELECT * FROM CONTRAT_PRIX ";
        List<Map<String, Object>> prix = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(prix);
    }
    //ADDING CONTRAT PRIX
    @CrossOrigin
    @GetMapping("/Priceadd")
    public ResponseEntity<String> Priceadd(
            @RequestParam("room_catg") String category,
            @RequestParam("codpension") String pension,
            @RequestParam("codContrat") String codC,
            @RequestParam("codPeriod") Integer codP,
            @RequestParam("adultpr") Long adultpr,
            @RequestParam("childpr") Long childpr,
            @RequestParam("babypr") Long babypr

    ) {

        String truncatedCategory = category.substring(0, Math.min(category.length(), 10));
        String request = "INSERT INTO CONTRAT_PRIX (COD_CONTRAT, COD_PERIODE, COD_PENS, CAT_ROOM, PRIX_ADL, PRIX_ENF, PRIX_BEB) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(request, codC,codP,pension,truncatedCategory,adultpr,childpr,babypr);
        return ResponseEntity.ok("Added Successfully");
    }
    //DELETE PRICE
    @CrossOrigin
    @DeleteMapping("/deletePrice")
    public ResponseEntity<String> deleteStock(@RequestParam("cont") String codC,
                                              @RequestParam("periodID") String codP,
                                              @RequestParam("pension") String codpens,
                                              @RequestParam("roomctg") String roomctg
    ) {
        String request = "DELETE FROM CONTRAT_PRIX WHERE COD_CONTRAT = ? AND COD_PERIODE = ? AND COD_PENS = ? AND CAT_ROOM = ?";
        jdbcTemplate.update(request, codC, codP,codpens,roomctg);
        return ResponseEntity.ok("Deleted successfully");

    }
    //UPDATE PRICE
    @CrossOrigin
    @GetMapping("/updatePrice")
    public ResponseEntity<String> updatePrice(
            @RequestParam("codcontrat") String codc,
            @RequestParam("codperiod") Integer codp,
            @RequestParam("roomcatg") String category,
            @RequestParam("codpension") String pension,
            @RequestParam("adlprice") Integer adlprice,
              @RequestParam("childprice") Integer childprice,
            @RequestParam("bbprice") Integer bebprice
            )

    {

        String deleteSql = "DELETE FROM CONTRAT_PRIX WHERE COD_CONTRAT = ? AND COD_PERIODE = ? AND COD_PENS = ? AND CAT_ROOM = ?";
        jdbcTemplate.update(deleteSql,codc,codp,pension,category);
        String truncatedCategory = category.substring(0, Math.min(category.length(), 10));
        // Insert the updated message with the new values
        String insertSql = "INSERT INTO CONTRAT_PRIX (COD_CONTRAT, COD_PERIODE, CAT_ROOM, COD_PENS, PRIX_ADL, PRIX_ENF, PRIX_BEB) VALUES (?, ?, ?, ?,?,?,?)";
        jdbcTemplate.update(insertSql, codc, codp, truncatedCategory, pension, adlprice, childprice, bebprice);

        return ResponseEntity.ok("Updated successfully");
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
    @CrossOrigin
    @GetMapping("/contratperiodesadd")
    public ResponseEntity<String> addContratperiode(
            @RequestParam("date_debCP") Long date_debCP,
            @RequestParam("date_finCP") Long date_finCP,
            @RequestParam("codC") String codC,
             @RequestParam("codP") String codP
    ) {
        // Convert the Unix timestamps to Java Timestamps if necessary
        Timestamp dateDebCP = new Timestamp(date_debCP * 1000);
        Timestamp dateFinCP = new Timestamp(date_finCP * 1000);

        String request = "INSERT INTO CONTRAT_PERIODE (COD_CONTRAT,COD_PERIODE,DEBUT_PERIODE,FIN_PERIODE) VALUES(?,?,?,?)";
        jdbcTemplate.update(request, codC,codP, dateDebCP, dateFinCP);
        return ResponseEntity.ok("Added Successfully");
    }
    @CrossOrigin
    @DeleteMapping("/deletecontratperiodes")
    public ResponseEntity<String> deletecontratperiodes(@RequestParam("cont") String cont,
                                                           @RequestParam("cod_per") Integer period    ) {
        String request = "DELETE FROM CONTRAT_PERIODE WHERE COD_CONTRAT= ? AND COD_PERIODE = ?";
        jdbcTemplate.update(request, cont, period );
        return ResponseEntity.ok("Deleted successfully");

    }
    @CrossOrigin
    @GetMapping("/updateContratperiodes")
    public ResponseEntity<String> updateContratperiodes(
            @RequestParam("codcontrat") String codc,
            @RequestParam("codper") Integer codp,


            @RequestParam("debutPeriod") Long debp,
            @RequestParam("finperiod") Long finp)

    {   Timestamp dateDebCP = new Timestamp(debp * 1000);
        Timestamp dateFinCP = new Timestamp(finp * 1000);

        String delete = "DELETE FROM CONTRAT_PERIODE WHERE COD_CONTRAT = ? ";
        jdbcTemplate.update(delete, codc);


        String insert = "INSERT INTO CONTRAT_PERIODE (COD_CONTRAT, COD_PERIODE, DEBUT_PERIODE, FIN_PERIODE) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insert, codc, codp, dateDebCP, dateFinCP);

        return ResponseEntity.ok("Updated successfully");
    }
    //SOCIETE
    @CrossOrigin
    @GetMapping("/societes")
    public ResponseEntity<List<Map<String, Object>>> getSocietes() {
        String request = "SELECT * FROM SOCIETE ";
        List<Map<String, Object>> societe = jdbcTemplate.queryForList(request);
        return ResponseEntity.ok(societe);
    }
    @CrossOrigin
    @GetMapping("/updateSociete")
    public ResponseEntity<String> updateSociete(
            @RequestParam("codsoc") String cods,
            @RequestParam("lib") String lib,
            @RequestParam("abrs") String abrs,
            @RequestParam("adr") String adr,
            @RequestParam("finalmatsoc") String mat,
            @RequestParam("tells") String tell,
            @RequestParam("maill") String mail,
            @RequestParam("web") String web,
            @RequestParam("rib") String rib,
            @RequestParam("fax") String fax )


    {

        String delete = "DELETE FROM SOCIETE WHERE COD_SOC = ? ";
        jdbcTemplate.update(delete, cods);


        String insert = "INSERT INTO SOCIETE (COD_SOC, LIB_SOC, ABR_SOC,MAT_SOC,ADR_SOC,TEL_SOC,FAX_SOC,MAIL_SOC,WEB_SOC,RIB_SOC) VALUES (?, ?, ?, ?,?,?,?,?,?,?)";
        jdbcTemplate.update(insert, cods,lib, abrs, mat,adr, tell,fax,mail,web,rib);

        return ResponseEntity.ok("Updated successfully");
    }
}*/
