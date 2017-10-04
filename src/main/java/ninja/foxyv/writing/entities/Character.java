package ninja.foxyv.writing.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Character {
    public String firstName;
    public String lastName;
    public String weaknesses;
    public String strengths;
    public String secret;
    public String goals;
    public String skinColor;
    public String sexuality;
    public String gender;
    public String hairColor;
    public String hairStyle;
}
