package uk.ac.ebi.gentar.clients;

import org.gentar.biology.gene_list.GeneListDTO;
import org.gentar.biology.plan.PlanDTO;
import org.gentar.biology.project.ProjectDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class GenTarClient {

    private RestTemplate restTemplate;
    private String gentarBaseUrl;//the base projects url e.g. String projectsUrl="http://localhost:8080/api/projects";

    /**
     * the base gentar url e.g. String projectsUrl="http://localhost:8080/"
     *
     * @param gentarBaseUrl
     * @throws URISyntaxException
     */
    public GenTarClient(String gentarBaseUrl) throws URISyntaxException {
        this.gentarBaseUrl = gentarBaseUrl;
        setUpProjectsRestTemplate();
    }

    private void setUpProjectsRestTemplate() {

        restTemplate = RestJsonUtil.setUpRestTemplate();
    }

    /**
     * for project requests where no authentication needed
     *
     * @param markerSymbol
     * @param workUnitName
     * @param tpn
     * @param page
     * @param size
     * @return
     * @throws URISyntaxException
     */
    public ResponseEntity<PagedModel<ProjectDTO>> getProjects(String markerSymbol, String workUnitName, String tpn, Integer page, Integer size) throws URISyntaxException {
        return this.getProjects(null, markerSymbol, workUnitName, tpn, page, size);
    }


    public ResponseEntity<PagedModel<ProjectDTO>> getProjects(String accessToken, String markerSymbol, String workUnitName, String tpn, Integer page, Integer size) throws URISyntaxException {
        HttpHeaders headers = RestJsonUtil.getHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        Map<String, String> params = new HashMap<>();
        if (page == null) {
            page = 0;
        }
        params.put("page", page.toString());
        if (size == null) {
            size = 20;
        }
        params.put("size", size.toString());

        String projectsUrl = gentarBaseUrl + "api/projects?page={page}&size={size}";

        if (markerSymbol != null) {
            params.put("markerSymbol", markerSymbol);
            projectsUrl = projectsUrl + "&markerSymbol={markerSymbol}";
        }
        if (workUnitName != null) {
            params.put("workUnitName", workUnitName);
            projectsUrl = projectsUrl + "&workUnitName={workUnitName}";
        }

        ResponseEntity<PagedModel<ProjectDTO>> response = restTemplate.exchange(projectsUrl, HttpMethod.GET, entity,
                new ParameterizedTypeReference<PagedModel<ProjectDTO>>() {
                }, params);
        return response;
    }

    /**
     * when no token required
     *
     * @param page
     * @param size
     * @return
     * @throws URISyntaxException
     */
    public ResponseEntity<PagedModel<PlanDTO>> getPlans(Integer page, Integer size) throws URISyntaxException {
        return this.getPlans(null, page, size);
    }

    public ResponseEntity<PagedModel<PlanDTO>> getPlans(String accessToken, Integer page, Integer size) throws URISyntaxException {
        HttpHeaders headers = RestJsonUtil.getHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        Map<String, String> params = new HashMap<>();
        if (page == null) {
            page = 0;
        }
        params.put("page", page.toString());
        if (size == null) {
            size = 20;
        }
        params.put("size", size.toString());

        String plansUrl = gentarBaseUrl + "api/plans?page={page}&size={size}";
        ResponseEntity<PagedModel<PlanDTO>> response = restTemplate.exchange(plansUrl, HttpMethod.GET, entity,
                new ParameterizedTypeReference<PagedModel<PlanDTO>>() {
                }, params);
        return response;
    }

    public ResponseEntity<PagedModel<GeneListDTO>> getGeneList(String consortium, Integer page, Integer size) throws URISyntaxException {
        return this.getGeneList(null, consortium, page, size);
    }

    public ResponseEntity<PagedModel<GeneListDTO>> getGeneList(String accessToken, String consortium, Integer page, Integer size) throws URISyntaxException {

        if (consortium == null) {
            return null;
        }

        HttpHeaders headers = RestJsonUtil.getHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        Map<String, String> params = new HashMap<>();
        if (page == null) {
            page = 0;
        }
        params.put("page", page.toString());
        if (size == null) {
            size = 20;
        }
        params.put("size", size.toString());
        if(consortium!=null) {
            params.put("consortium", consortium);
        }
        String geneListUrl = gentarBaseUrl + "api/geneList/{consortium}/content";
        System.out.println("retrieving geneLists from " + geneListUrl);
        ResponseEntity<PagedModel<GeneListDTO>> response = restTemplate.exchange(geneListUrl, HttpMethod.GET, entity,
                new ParameterizedTypeReference<PagedModel<GeneListDTO>>() {
                }, params);
        return response;
    }
}
