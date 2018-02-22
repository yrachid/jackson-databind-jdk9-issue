package pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonAllArgs {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("age")
    private final Integer age;

    @JsonCreator
    public PersonAllArgs(String name, Integer age) {
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
