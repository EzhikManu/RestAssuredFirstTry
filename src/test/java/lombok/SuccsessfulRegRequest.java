package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccsessfulRegRequest {
@JsonProperty("email")
    private String email;
@JsonProperty("password")
    private String password;
}
