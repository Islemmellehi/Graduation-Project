package com.example.vic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RecommendationController {



    public String testSample ;
    @CrossOrigin
    @GetMapping("/recommendationdata")
    public String sendRecommendationData(
            @RequestParam("clientSF") String clientSF
    ){

        testSample = clientSF;
        return clientSF;
    }

    //testing the results retrieving
    @CrossOrigin
    @GetMapping("/status")
    public String StatusExample(){

        return testSample;
    }

    public ArrayList Results ;
    @CrossOrigin
    @GetMapping("/recommendationresponse")
    public List<String> receiveRecommendationResponse(
            @RequestParam("rec1") String rec1,
            @RequestParam("rec2") String rec2,
            @RequestParam("rec3") String rec3) {

        List<String> recommendations = new ArrayList<>();
        recommendations.add(rec1);
        recommendations.add(rec2);
        recommendations.add(rec3);

        Results = (ArrayList) recommendations;
        return recommendations;
    }
    @CrossOrigin
    @GetMapping("/response")
    public String resultsRec() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Results);
        return json;
    }


    @CrossOrigin
    @GetMapping("/run-python-code")
    @ResponseBody
    public String runPythonCode() {
        String output = null;
        try {
            // Replace "python" with the appropriate command or path to the Python executable
            Process process = new ProcessBuilder("python", "C:\\Users\\islem\\OneDrive\\Bureau\\vic\\vic\\src\\main\\java\\com\\example\\vic\\recommendations.py")
                    .redirectErrorStream(true)
                    .start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            output = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }


}
