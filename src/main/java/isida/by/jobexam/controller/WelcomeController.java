package isida.by.jobexam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер работы с приветственной страницей
 */
@RestController
public class WelcomeController {

    @GetMapping(value = "/welcome/message")
    public ResponseEntity<String> getWelcomeText(){
        return ResponseEntity.ok("Добро пожаловать в сервис картинок!");
    }
}
