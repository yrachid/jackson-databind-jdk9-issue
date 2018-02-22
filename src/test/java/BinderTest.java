import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BinderTest {

    @Test
    public void it_binds_a_json_to_a_person_object() throws IOException {
        String json = "{\"name\": \"Lombok\", \"age\": 10}";

        Person person = Binder.bind(json);

        assertEquals("Lombok", person.getName());
        assertTrue(person.getAge() == 10);
    }

    @Test
    public void it_writes_a_person_as_a_string() throws JsonProcessingException {
        Person person = new Person("Lombok", 10);

        String json = "{\"name\":\"Lombok\",\"age\":10}";

        assertEquals(json, Binder.asString(person));
    }

}
