package uk.ac.ebi.gentar.reporter.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
//import org.springframework.data.annotation.Id;
import uk.ac.ebi.impc_prod_tracker.web.dto.project.ProjectDTO;

import javax.annotation.Resource;
import java.util.List;
@Resource
public class ProjectsWrapper {

    //@Id
    //public String id;

    public List<ProjectDTO> getProjectDToes() {
        return projectDToes;
    }

    public void setProjectDToes(List<ProjectDTO> projectDToes) {
        this.projectDToes = projectDToes;
    }

    @JsonProperty("projectDToes")
    List<ProjectDTO> projectDToes;

    @Override
    public String toString() {
        return "ProjectsWrapper{" +
                "projectDToes=" + projectDToes +
                '}';
    }
}
