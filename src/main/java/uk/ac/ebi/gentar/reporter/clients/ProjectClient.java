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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.impc_prod_tracker.web.dto.project.ProjectDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class ProjectClient {

    private RestTemplate restTemplate;
    private  HttpHeaders projectHeaders;

    public ProjectClient() throws URISyntaxException {
        setUpProjectsRestTemplate();

        

    }

    private void setUpProjectsRestTemplate() {

        restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());
        mapper.registerModule(new JavaTimeModule());//serialize and deserialize dates

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
        converter.setObjectMapper(mapper);
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        this.restTemplate = restTemplateBuilder.additionalMessageConverters(Arrays.asList(converter)).build();
        projectHeaders = new HttpHeaders();
        projectHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    public void getProjects(String accessToken) throws URISyntaxException {

        projectHeaders.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", projectHeaders);
        String projectsUrl="http://localhost:8080/api/projects";
        Map<String, String> params = new HashMap<>();
        ResponseEntity<PagedModel<ProjectDTO>> response = restTemplate.exchange(projectsUrl, HttpMethod.GET, entity,
                new ParameterizedTypeReference<PagedModel<ProjectDTO>>() {
                }, params);
        System.out.println("projects response="+ response);
        PagedModel projectsWrapper= response.getBody();
        System.out.println("response body="+projectsWrapper);
    }
}
