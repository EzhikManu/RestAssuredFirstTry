package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessfulRegRequestData {
    @JsonProperty("")
    SuccsessfulRegRequest req;
}
