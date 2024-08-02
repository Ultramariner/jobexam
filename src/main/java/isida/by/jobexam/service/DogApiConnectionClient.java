package isida.by.jobexam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class DogApiConnectionClient {

    private final RestTemplate restTemplate;
    @Value("${server.dog-api-path}")
    private String apiPath;

    public String getAllBreeds() {
        return restTemplate.getForEntity(apiPath + "/breeds/list/all", String.class).getBody();
    }

    public String getBreedImage(String breed) {
        return restTemplate.getForEntity( apiPath +"/breed/" + breed + "/images/random", String.class).getBody();
    }

}
