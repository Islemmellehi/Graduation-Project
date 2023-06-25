package com.example.vic.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class Connectioncheck{


    public String testconnection ;

    @CrossOrigin
    @GetMapping("/connection")
    public String ConnectionStatus(
            @RequestParam("connected") String conntected){
       testconnection = conntected;
        return testconnection;
    }

    //testing the results retrieving
    @CrossOrigin
    @GetMapping("/statusconnection")
    public String StatusExample(){

        return testconnection ;
    }

}



