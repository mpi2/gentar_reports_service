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
package uk.ac.ebi.impc_prod_tracker.web.dto.plan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import uk.ac.ebi.impc_prod_tracker.web.dto.plan.phenotyping.PhenotypingAttemptDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.plan.production.breeding_attempt.BreedingAttemptDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.plan.production.crispr_attempt.CrisprAttemptDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.status_stamps.StatusStampsDTO;

import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PlanDTO
{
    @JsonIgnore
    private Long id;

    @NonNull
    private String pin;

    @NonNull
    private String tpn;

    private String funderName;
    private String workUnitName;
    private Boolean isActive;
    private String statusName;

    @JsonProperty("statusDates")
    private List<StatusStampsDTO> statusStampsDTOS;

    private String comment;
    private Boolean productsAvailableForGeneralPublic;

    @JsonProperty("typeName")
    private String planTypeName;

    private String attemptTypeName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("crisprAttemptAttributes")
    private CrisprAttemptDTO crisprAttemptDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("breedingAttemptAttributes")
    private BreedingAttemptDTO breedingAttemptDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("phenotypingAttemptAttributes")
    private PhenotypingAttemptDTO phenotypingAttemptDTO;
}
