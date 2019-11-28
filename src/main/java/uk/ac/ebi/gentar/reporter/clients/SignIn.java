package uk.ac.ebi.gentar.reporter.clients;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;

@NoArgsConstructor
@Resource
@Data
public class SignIn {
//"userName":"rwilson@ebi.ac.uk","accessToken":"eyJhbGciOiJ
    private String userName;
    private String accessToken;

    @Override
    public String toString() {
        return "SignIn{" +
                "username='" + userName + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
