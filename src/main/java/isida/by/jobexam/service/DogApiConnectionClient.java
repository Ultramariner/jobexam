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

    /**
     * Отправляет запроса стороннему api и получает ответ
     * @return Возвращает тело json с перечнем всех пород
     */
    public String getAllBreeds() {
        return restTemplate.getForEntity(apiPath + "/breeds/list/all", String.class).getBody();
    }

    /**
     * Отправляет запроса стороннему api и получает ответ
     * @param breed Уникальное имя породы
     * @return Возвращает тело json со ссылкой на изображение
     */
    public String getBreedImage(String breed) {
        return restTemplate.getForEntity( apiPath +"/breed/" + breed + "/images/random", String.class).getBody();
    }

}
