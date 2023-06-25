package com.example.vic;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.*;

@RestController
public class OracleFunctionController {
    private AtomicLong LigResaCounter = new AtomicLong(1);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OracleFunctionController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @CrossOrigin
    @GetMapping("/f_get_dispo_room")
    public String callOracleFunction(
            @RequestParam("wnb_adl") int wnbAdl,
            @RequestParam("wnb_enf") int wnbEnf,
            @RequestParam("wnb_beb") int wnbBeb,
            @RequestParam("wdate_arr") java.sql.Date wdateArr,
            @RequestParam("wdate_dep") java.sql.Date wdateDep,
            @RequestParam("wcod_pens") String wcodPens


    ) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("f_get_dispo_room")
                .declareParameters(
                        new SqlParameter("wsession_id", Types.VARCHAR),
                        new SqlParameter("wnb_adl", Types.NUMERIC),
                        new SqlParameter("wnb_enf", Types.NUMERIC),
                        new SqlParameter("wnb_beb", Types.NUMERIC),
                        new SqlParameter("wdate_arr", Types.DATE),
                        new SqlParameter("wdate_dep", Types.DATE),
                        new SqlParameter("wcod_pens", Types.VARCHAR),
                        new SqlOutParameter("return", Types.INTEGER));

        Map<String, Object> inParams = new HashMap<>();
        String session_Id = jdbcTemplate.queryForObject("SELECT userenv('SESSIONID') FROM DUAL", String.class);
        inParams.put("wsession_id", session_Id);
        inParams.put("wnb_adl", wnbAdl);
        inParams.put("wnb_enf", wnbEnf);
        inParams.put("wnb_beb", wnbBeb);
        inParams.put("wdate_arr", wdateArr);
        inParams.put("wdate_dep", wdateDep);
        inParams.put("wcod_pens", wcodPens);

        Map<String, Object> outParams = call.execute(inParams);

        int outputValue = (int) outParams.get("return");

        if (outputValue == 1) {
            getAllAvailableRooms(session_Id);
            return (session_Id);
        } else {
            return ("oopsy doopsy no rooms are available");
        }

    }


    @CrossOrigin
    @GetMapping("/availablerooms/{param}")
    public List<Map<String, Object>> getAllAvailableRooms(@PathVariable ("param") String param) {

        String request = "SELECT * FROM WORK_ROOM WHERE SESSION_ID = ?";


        return jdbcTemplate.queryForList(request, param);

    }

    @CrossOrigin
    @GetMapping("/roomdetails/{id}")
    public List<Map<String, Object>>getRoomDetails(@PathVariable ("id") String id) {

        String request = "SELECT * FROM ROOM_CATEGORIE WHERE CAT_ROOM = ?";


        return jdbcTemplate.queryForList(request,id);
    }
    @CrossOrigin

    @GetMapping("/reservation/senddata")
    public ResponseEntity<String> sendReservationData(
            @RequestParam("rdate_arr") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date dateArr,
            @RequestParam("rdate_dep") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date dateDep,
            @RequestParam("rdat_saisie") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date dateSaisie,
            @RequestParam("rnb_chambre") int nbChambre,
            @RequestParam("rnom_resa") String nomResa,
            @RequestParam("rprn_resa") String prnResa,
            @RequestParam("rmail_resa") String mailResa,
            @RequestParam("rtel_resa") String telResa) {

        Long codResa = jdbcTemplate.queryForObject("SELECT MAX(COD_RESA) FROM RESERVATION", Long.class);
        long newCodResa = (codResa != null) ? codResa + 1 : 1;

        String insertQuery = "INSERT INTO RESERVATION (COD_RESA, DATE_ARR, DATE_DEP, DAT_SAISIE, NB_CHAMBRE, NOM_RESA, PRN_RESA, MAIL_RESA, TEL_RESA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertQuery, newCodResa, dateArr, dateDep, dateSaisie, nbChambre, nomResa, prnResa, mailResa, telResa);

        return new ResponseEntity<>("Data saved successfully", HttpStatus.CREATED);
    }




    @CrossOrigin
    @GetMapping("/reservationdetails/senddata")
    public ResponseEntity<String> ReservationDetailsData(
            @RequestParam("CAT_ROOM") String catRoom,
            @RequestParam("COD_PENS") String codPens,
            @RequestParam("NB_ADL") int nbAdl,
            @RequestParam("NB_ENF") int nbEnf,
            @RequestParam("NB_BEB") int nbBeb) {

        Long codResa = jdbcTemplate.queryForObject("SELECT MAX(COD_RESA) FROM RESERVATION", Long.class);
        if (codResa == null) {
            // Handle the case when no reservation exists in the RESERVATION table
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservation found");
        }

        long ligResa = LigResaCounter.incrementAndGet();

        String pensionId = jdbcTemplate.queryForObject("SELECT COD_PENS FROM PENSION WHERE COD_PENS = ?", String.class, codPens);

        if (pensionId != null) {
            String roomCategorieId = jdbcTemplate.queryForObject("SELECT CAT_ROOM FROM ROOM_CATEGORIE WHERE CAT_ROOM = ?", String.class, catRoom);

            if (roomCategorieId != null) {
                jdbcTemplate.update("INSERT INTO RESERVATION_DETAIL (COD_RESA, LIG_RESA, CAT_ROOM, COD_PENS, NB_ADL, NB_ENF, NB_BEB) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)",
                        codResa, ligResa, catRoom, pensionId, nbAdl, nbEnf, nbBeb);

                return ResponseEntity.status(HttpStatus.CREATED).body("Data saved successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room category not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pension not found");
        }
    }

}







