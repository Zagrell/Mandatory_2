package gruppe1.backend;


import gruppe1.backend.models.Summoner;
import gruppe1.backend.repositories.RiotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BackendApplication.class, args);
    }


}

