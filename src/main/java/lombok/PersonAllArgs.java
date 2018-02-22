package lombok;

import com.fasterxml.jackson.annotation.JsonCreator;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
public class PersonAllArgs {
    private String name;
    private Integer age;
}
