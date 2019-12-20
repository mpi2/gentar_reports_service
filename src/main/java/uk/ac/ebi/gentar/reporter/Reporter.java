package uk.ac.ebi.gentar.reporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVWriter;
import org.gentar.biology.gene_list.GeneListDTO;
import org.gentar.biology.intention.OrthologDTO;
import org.gentar.biology.intention.ProjectIntentionDTO;
import org.gentar.biology.intention.ProjectIntentionGeneDTO;
import org.gentar.biology.plan.PlanDTO;
import org.gentar.biology.project.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


import uk.ac.ebi.gentar.clients.*;
@SpringBootApplication
public class Reporter implements CommandLineRunner {


        private static final Logger logger = LoggerFactory.getLogger(Reporter.class);
    private static final String gentarBaseUrl ="https://www.gentar.org/production-tracker-sandbox-api/";//"http://localhost:8080/api/";//"https://www.gentar.org/production-tracker-sandbox-api/api/"
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
            String token = getToken(userName, password, gentarBaseUrl);
            genTarClient =new GenTarClient(gentarBaseUrl);
            Collection<ProjectDTO> projects=getAllProjects(token);
            String filePath="/Users/jwarren/GentTaRReports/csvTest.csv";
            this.writeProjects(projects, filePath);
            //getAllPlans(token);
            //getGeneList(token);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void writeProjects(Collection<ProjectDTO> projects, String  filePath) throws JsonProcessingException {
            // first create file object for file placed at location
            // specified by filepath
            File file = new File(filePath);
            try {
                // create FileWriter object with file as parameter
                FileWriter outputfile = new FileWriter(file);

                // create CSVWriter object filewriter object as parameter
                CSVWriter writer = new CSVWriter(outputfile);

                // adding header to csv
                String[] header = {"TPN", "Allele Intentions","Gene Symbol/Location","Best Human Orthologue","Assignment Status","Is Active", "Privacy Name"};
                writer.writeNext(header);

                // add data to csv
                int i=0;
                for(ProjectDTO proj:projects){
                    if(i==0)System.out.println(proj);
                    String alleleTypeName="Null";
                    String geneSymbol="";
                    String bestHumanOrthologue="";
                    for(ProjectIntentionDTO intention:proj.getProjectIntentionDTOS()){
                        ArrayList<String> intentions=new ArrayList<>();
                        intentions.add(intention.getAlleleTypeName());
                        if(intention.getAlleleTypeName()!=null){
                            alleleTypeName=intention.getAlleleTypeName();
                        }
                        //intentions.add(intention.getIntentionTypeName());
                        //intentions.add(intention.getMolecularMutationTypeName());
                        ProjectIntentionGeneDTO gene = intention.getProjectIntentionGeneDTO();
                        if(gene.getGeneDTO().getSymbol()!=null){
                            geneSymbol=gene.getGeneDTO().getSymbol();
                        };
                        if(gene.getBestOrthologs()!=null) {
                            for (OrthologDTO bestOrth : gene.getBestOrthologs()) {
                                bestHumanOrthologue = bestHumanOrthologue+" "+ bestOrth.getSymbol();
                            }
                        }
                    }


                    String[] overview = { proj.getTpn(), alleleTypeName, geneSymbol, bestHumanOrthologue, proj.getAssignmentStatusName(),  proj.getIsActive().toString(), proj.getPrivacyName() };
                    //List<String> arrlist= new ArrayList<String>(Arrays.asList(overview));
                    writer.writeNext(overview);
                    i++;
                }


//                String[] data2 = { "Suraj", "10", "630" };
//                writer.writeNext(data2);

                // closing writer connection
                writer.close();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



    }

    private Collection<ProjectDTO> getAllProjects(String token) throws URISyntaxException {
        //token valid for 1 hour so can use it till then.
        ResponseEntity<PagedModel<ProjectDTO>> response = genTarClient.getProjects(token, null,null,null,0, 1000000);
        System.out.println("projects response="+ response);
        PagedModel pageModel= response.getBody();
        System.out.println("number of projects available="+ pageModel.getMetadata().getTotalElements());
        Collection projects = pageModel.getContent();
        System.out.println("response body="+projects.size());
        return projects;
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

    private void getGeneList(String token) throws URISyntaxException {
        //token valid for 1 hour so can use it till then.
        String consortium="CMG";
        ResponseEntity<PagedModel<GeneListDTO>> response = genTarClient.getGeneList(token, consortium,0, 10000);
        System.out.println("plans response="+ response);
        PagedModel pageModel= response.getBody();
        System.out.println("number of gene lists available="+ pageModel.getMetadata().getTotalElements());
        Collection geneList = pageModel.getContent();
        System.out.println("response body="+geneList.size());

    }

    private String getToken(String userName, String password, String gentarBaseUrl) {
        SignInClient signIn=new SignInClient();
        return signIn.getAuthorizationToken(userName, password, gentarBaseUrl);
    }
}
