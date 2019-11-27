/*******************************************************************************
 * Copyright 2019 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 *******************************************************************************/
package uk.ac.ebi.impc_prod_tracker.web.dto.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import uk.ac.ebi.impc_prod_tracker.web.dto.intention.ProjectIntentionDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.species.SpeciesDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.status_stamps.StatusStampsDTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Representation of a Project that will be presented in the API.
 */
@Resource
@Data
@NoArgsConstructor
public class ProjectDTO extends RepresentationModel
{
    private String tpn;
    private String assignmentStatusName;

    @JsonProperty("assignmentStatusStamps")
    private List<StatusStampsDTO> statusStampsDTOS;

    @JsonProperty("externalReference")
    private String projectExternalRef;
    private Boolean withdrawn;
    private Boolean recovery;

    @JsonProperty("projectIntentions")
    private List<ProjectIntentionDTO> projectIntentionDTOS;

    private String comment;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("privacyName")
    private String privacyName;

    @JsonProperty("speciesAttributes")
    private List<SpeciesDTO> projectSpeciesDTOs;

    @JsonProperty("projectConsortia")
    private List<ProjectConsortiumDTO> projectConsortiumDTOS;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("isObjectRestricted")
    private Boolean isObjectRestricted;
}
