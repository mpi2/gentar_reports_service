package uk.ac.ebi.gentar.reporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import uk.ac.ebi.gentar.reporter.clients.ProjectClient;

import java.util.Arrays;

@SpringBootApplication
public class Reporter implements CommandLineRunner {


        private static final Logger logger = LoggerFactory.getLogger(Reporter.class);






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

        ProjectClient projClient=new ProjectClient();
        try {
            projClient.getProjects();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
