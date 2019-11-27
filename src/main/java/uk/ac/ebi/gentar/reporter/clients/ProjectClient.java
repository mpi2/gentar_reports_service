package uk.ac.ebi.gentar.reporter.clients;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.impc_prod_tracker.web.dto.project.ProjectDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class ProjectClient {

    private final String accessToken;
    private RestTemplate restTemplate;
    private HttpEntity<String> entity;
    private  HttpHeaders headers;

    public ProjectClient() throws URISyntaxException {
        setUpProjectsRestTemplate();
        this.accessToken=getAuthorizationToken();
        this.getProjects(accessToken);
        

    }

    private String getAuthorizationToken() {
        //to get authorization token we need to send a post
        //http://127.0.0.1:8080/auth/signin
        String accessToken="eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4cGxvcmUuYWFpLmViaS5hYy51ay9zcCIsImp0aSI6IktTdDkwT3NwenkwaUxwZV9RbldSVXciLCJpYXQiOjE1NzQ4NjE1NTMsInN1YiI6InVzci1iNzkxZjAyYi1lN2E1LTRlNTEtYjI1YS0zNmQ1MDIwMTFlZTciLCJlbWFpbCI6InJ3aWxzb25AZWJpLmFjLnVrIiwibmlja25hbWUiOiJyd2lsc29uQGViaS5hYy51ayIsIm5hbWUiOiJSb2JlcnQgV2lsc29uIiwiZG9tYWlucyI6WyJzZWxmLmdlbnRhci1tYWludGFpbmVyLWRvbWFpbiJdLCJleHAiOjE1NzQ4NjUxNTN9.O_dyLLtUSpqnaBGJlVBqBYatl_GyZpmiANOXXUPVHT623w4vBXIwALzj-RWlZhxminJYLEMTrxww4NfFfGBLnXU_wnSzNhIDEL0sCmQAg7sjs7nPByL10y-uzK3_YzIpPPkCoRztYc_QJJ7MQMVFi-lx1H0gRfPKuMuFqOUPozl3Aapf0Iii8Qye43N7rgYcMPMPNEwuo-WOJRxEiy1YVIXQLejPPeEpiWyHhdG533eO17qZUs3XJmlYHdt-Hp8rmSALQXkQ_1dw6koMSnFUT56anZvk0VXy7EzuRSlHKI1AM_wE7YK5eD5Ndokp15MrlpYYbnGd6Vyo-pV47YgSVQ";
        return accessToken;
    }

    private void setUpProjectsRestTemplate() {
        HttpEntity<ProjectDTO> request = new HttpEntity<>(new ProjectDTO());
        restTemplate = new RestTemplate();
        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());
        mapper.registerModule(new JavaTimeModule());//serialize and deserialize dates

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
        converter.setObjectMapper(mapper);
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        this.restTemplate = restTemplateBuilder.additionalMessageConverters(Arrays.asList(converter)).build();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //params.put("geneAccession", geneAccession);
    }

    public void getProjects(String accessToken) throws URISyntaxException {
        headers.set("Authorization", "Bearer "+accessToken);
        entity = new HttpEntity<>("body", headers);
        String projectsUrl="http://localhost:8080/api/projects";
        Map<String, String> params = new HashMap<>();
        ResponseEntity<PagedModel<ProjectDTO>> response = restTemplate.exchange(projectsUrl, HttpMethod.GET, entity,
                new ParameterizedTypeReference<PagedModel<ProjectDTO>>() {
                }, params);
        //the response even when string is only returning 0 results with fresh token so we need more info sent???
        System.out.println("projects response="+ response);
        PagedModel projectsWrapper= response.getBody();
        System.out.println("response body="+projectsWrapper);

        // assertThat(foo, notNullValue());
        // assertThat(foo.getName(), is("bar"));
    }
}
