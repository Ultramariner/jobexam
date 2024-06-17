package isida.by.jobexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер выполняет редирект на Frontend
 */
@Controller
public class VueRedirectController {

    /**
     * Перенаправляет на index.html, если адрес начинается с /vue/
     * @param path
     * @return
     */
    @RequestMapping(value = "/vue/{path:[^\\.]*}")
    public String redirect(@PathVariable String path) {
        return "forward:/";
    }
}
