package uk.ac.ebi.gentar.reporter.clients;
import org.springframework.hateoas.PagedModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.impc_prod_tracker.web.dto.plan.PlanDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.project.ProjectDTO;

import java.net.URISyntaxException;
import java.util.*;

import static uk.ac.ebi.gentar.reporter.clients.RestJsonUtil.*;

public class GenTarClient {

    private RestTemplate restTemplate;
    private String apiBaseUrl;//the base projects url e.g. String projectsUrl="http://localhost:8080/api/projects";

    /**
     * the base projects url e.g. String projectsUrl="http://localhost:8080/api/"
     * @param apiBaseUrl
     * @throws URISyntaxException
     */
    public GenTarClient(String apiBaseUrl) throws URISyntaxException {
        this.apiBaseUrl =apiBaseUrl;
        setUpProjectsRestTemplate();
    }

    private void setUpProjectsRestTemplate() {

        restTemplate= setUpRestTemplate();
    }

    public ResponseEntity<PagedModel<ProjectDTO>> getProjects(String accessToken, Integer page, Integer size) throws URISyntaxException {
        HttpHeaders headers= getHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        Map<String, String> params = new HashMap<>();
        if(page==null){
            page=0;
        }
        params.put("page",page.toString());
        if(size==null) {
            size = 20;
        }
        params.put("size", size.toString());

        String projectsUrl=apiBaseUrl+"projects?page={page}&size={size}";
        ResponseEntity<PagedModel<ProjectDTO>> response = restTemplate.exchange(projectsUrl, HttpMethod.GET, entity,
                new ParameterizedTypeReference<PagedModel<ProjectDTO>>() {
                }, params);
        return response;
    }

    public ResponseEntity<PagedModel<PlanDTO>> getPlans(String accessToken, Integer page, Integer size) throws URISyntaxException {
        HttpHeaders headers= getHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        Map<String, String> params = new HashMap<>();
        if(page==null){
            page=0;
        }
        params.put("page",page.toString());
        if(size==null) {
            size = 20;
        }
        params.put("size", size.toString());

        String plansUrl=apiBaseUrl+"plans?page={page}&size={size}";
        ResponseEntity<PagedModel<PlanDTO>> response = restTemplate.exchange(plansUrl, HttpMethod.GET, entity,
                new ParameterizedTypeReference<PagedModel<PlanDTO>>() {
                }, params);
        return response;
    }
}
