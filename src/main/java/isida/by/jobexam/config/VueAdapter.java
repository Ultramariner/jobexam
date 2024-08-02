package isida.by.jobexam.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

@Component
public class VueAdapter implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //todo (7) to property
                .allowedOrigins("http://localhost:5173") // Замените на URL вашего фронтенда
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

}
