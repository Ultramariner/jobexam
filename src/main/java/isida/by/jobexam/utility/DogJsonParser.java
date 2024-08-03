package isida.by.jobexam.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class DogJsonParser {

    public String parseToString (String response) throws JsonProcessingException {
        JsonNode root = ObjectMapperProvider.get().readTree(response);
        return root.get("message").textValue();
    }
}
