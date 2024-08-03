package isida.by.jobexam.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BreedJsonParser {

    public Map<String, List<String>> parseToMap (String response) throws JsonProcessingException {
        JsonNode jsonNode = ObjectMapperProvider.get().readTree(response);
        JsonNode messageNode = jsonNode.get("message");
        return ObjectMapperProvider.get().convertValue(messageNode, new TypeReference<>() {});
    }
}
