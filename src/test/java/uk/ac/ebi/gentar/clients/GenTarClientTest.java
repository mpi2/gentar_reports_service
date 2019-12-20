package uk.ac.ebi.gentar.clients;

import org.gentar.biology.gene_list.GeneListDTO;
import org.gentar.biology.plan.PlanDTO;
import org.gentar.biology.project.ProjectDTO;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GenTarClientTest {
    private static final String gentarBaseUrl ="https://www.gentar.org/production-tracker-sandbox-api/";//"http://localhost:8080/api/";//"https://www.gentar.org/production-tracker-sandbox-api/api/"
    private static GenTarClient genTarClient;
    @org.junit.jupiter.api.BeforeAll
    static void setUp() throws URISyntaxException {
        //String token = getToken(userName, password, gentarBaseUrl);
        genTarClient =new GenTarClient(gentarBaseUrl);
    }

    @org.junit.jupiter.api.AfterAll
    static void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getProjects() throws URISyntaxException {
        ResponseEntity<PagedModel<ProjectDTO>> projectsResponse = genTarClient.getProjects(null, null, null, null, null);
        assertTrue(projectsResponse.getBody().getContent().size()>0);
    }

    @org.junit.jupiter.api.Test
    void getPlans() throws URISyntaxException {
        ResponseEntity<PagedModel<PlanDTO>> plansResponse = genTarClient.getPlans(null, null);
        assertTrue(plansResponse.getBody().getContent().size()>0);

    }

    @org.junit.jupiter.api.Test
    void getGeneListIsForbidden()  {
        ResponseEntity<PagedModel<GeneListDTO>> geneListResponse = null;
        try {
            geneListResponse = genTarClient.getGeneList("CMG", null, null);
        } catch (URISyntaxException e) {
            System.out.println("printing custom stack trace");
            e.printStackTrace();
        }
        System.out.println("genelistResponse="+geneListResponse);
        assertTrue(geneListResponse==null);
    }
}
