package uk.ac.ebi.gentar.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.gentar.biology.project.ProjectDTO;

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
