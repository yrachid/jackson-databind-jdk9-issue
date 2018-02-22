import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.fasterxml.jackson.databind.MapperFeature.INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ObjectMapperTest {

    private static final String JSON = "{\"name\": \"Joe\", \"age\": 10}";

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.configure(INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES,false);
    }

    @Test
    public void lombok_no_args_works() throws IOException {

        lombok.PersonNoArgs person = mapper.readValue(JSON, lombok.PersonNoArgs.class);

        assertEquals("Joe", person.getName());
        assertTrue(person.getAge() == 10);
    }

    @Test
    public void pojo_no_args_works() throws IOException {

        pojo.PersonNoArgs person = mapper.readValue(JSON, pojo.PersonNoArgs.class);

        assertEquals("Joe", person.getName());
        assertTrue(person.getAge() == 10);
    }

    @Test
    public void lombok_all_args_fails() throws IOException {

        lombok.PersonAllArgs person = mapper.readValue(JSON, lombok.PersonAllArgs.class);

        assertEquals("Joe", person.getName());
        assertTrue(person.getAge() == 10);
    }

    @Test
    public void pojo_all_args_fails() throws IOException {

        pojo.PersonAllArgs person = mapper.readValue(JSON, pojo.PersonAllArgs.class);

        assertEquals("Joe", person.getName());
        assertTrue(person.getAge() == 10);
    }
}
