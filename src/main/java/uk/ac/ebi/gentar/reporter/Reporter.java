package uk.ac.ebi.gentar.reporter;

import org.gentar.biology.plan.PlanDTO;
import org.gentar.biology.project.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import uk.ac.ebi.gentar.reporter.clients.GenTarClient;
import uk.ac.ebi.gentar.reporter.clients.SignInClient;

import java.net.URISyntaxException;
import java.util.Collection;

@SpringBootApplication
public class Reporter implements CommandLineRunner {


        private static final Logger logger = LoggerFactory.getLogger(Reporter.class);
    private static final String apiBaseUrl="http://localhost:8080/api/";//"https://www.gentar.org/production-tracker-sandbox-api/api/"
    GenTarClient genTarClient;




    public static void main(String [] args) throws Exception {
        SpringApplication springApplication =
                new SpringApplicationBuilder()
                        .sources(Reporter.class)
                        .web(WebApplicationType.NONE)
                        .build();

        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("running Reporter application now!!!");
        if(args.length<2){
            System.err.println("Error: we need a username and password to proceed");
        }
        String userName=args[0];
        String password=args[1];

        try {
            String token = getToken(userName, password);
            genTarClient =new GenTarClient(apiBaseUrl);
            getAllProjects(token);
            getAllPlans(token);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getAllProjects(String token) throws URISyntaxException {
        //token valid for 1 hour so can use it till then.
        ResponseEntity<PagedModel<ProjectDTO>> response = genTarClient.getProjects(token, 0, 1000000);
        System.out.println("projects response="+ response);
        PagedModel pageModel= response.getBody();
        System.out.println("number of projects available="+ pageModel.getMetadata().getTotalElements());
        Collection projects = pageModel.getContent();
        System.out.println("response body="+projects.size());

    }

    private void getAllPlans(String token) throws URISyntaxException {
        //token valid for 1 hour so can use it till then.
        ResponseEntity<PagedModel<PlanDTO>> response = genTarClient.getPlans(token, 0, 10000);
        System.out.println("plans response="+ response);
        PagedModel pageModel= response.getBody();
        System.out.println("number of plans available="+ pageModel.getMetadata().getTotalElements());
        Collection plans = pageModel.getContent();
        System.out.println("response body="+plans.size());

    }

    private String getToken(String userName, String password) {
        SignInClient signIn=new SignInClient();
        return signIn.getAuthorizationToken(userName, password);
    }
}
