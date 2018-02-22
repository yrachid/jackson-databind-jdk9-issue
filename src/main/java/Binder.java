import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;

import static com.fasterxml.jackson.databind.MapperFeature.INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES;

public class Binder {

    public static Person bind(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.configure(INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES,false);

        return mapper.readValue(json, Person.class);
    }

    public static String asString(Person person) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        return mapper.writeValueAsString(person);
    }
}
