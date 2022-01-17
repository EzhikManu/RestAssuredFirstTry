package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateResponse {
    public String name;
    public String job;
    public String updatedAt;
}
