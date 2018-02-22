package pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonAllArgs {

    private final String name;
    private final Integer age;

    @JsonCreator
    public PersonAllArgs(
            @JsonProperty("name") String name,
            @JsonProperty("age") Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
