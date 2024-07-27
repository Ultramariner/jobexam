package isida.by.jobexam.controller;

import isida.by.jobexam.model.Dog;
import isida.by.jobexam.service.DogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequestMapping(path = "/vue/dogs")
public class DogController {

    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping
    public ModelAndView getBreeds() throws IOException {
        StringBuilder response = new StringBuilder();
        URL url = new URL ("https://dog.ceo/api/breeds/list/all");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        return new ModelAndView("index.html")
                .addObject("breeds", response.toString());
    }

    @PostMapping
    public ModelAndView getImageByBreed(@RequestParam(value = "breed") String breed) throws IOException {
        StringBuilder response = new StringBuilder();
        URL url = new URL ("https://dog.ceo/api/breed/" + breed + "/images/random");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        return new ModelAndView("index.html")
                .addObject("image", response.toString());
    }

    @PutMapping
    @ResponseBody
    public void save(@RequestBody Dog dog) throws IOException {
        dogService.save(dog);
    }
}