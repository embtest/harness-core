/*
 * Copyright 2022 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.cdng.creator.plan.environment;

import static io.harness.annotations.dev.HarnessTeam.CDP;

import io.harness.annotations.dev.OwnedBy;
import io.harness.cdng.environment.steps.EnvironmentStepParameters;
import io.harness.cdng.environment.yaml.EnvironmentPlanCreatorConfig;
import io.harness.yaml.utils.NGVariablesUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
@OwnedBy(CDP)
public class EnvironmentPlanCreatorConfigHelper {
  public EnvironmentStepParameters toEnvironmentStepParameters(
      EnvironmentPlanCreatorConfig environmentPlanCreatorConfig) {
    return EnvironmentStepParameters.builder()
        .environmentRef(environmentPlanCreatorConfig.getEnvironmentRef())
        .name(environmentPlanCreatorConfig.getName())
        .identifier(environmentPlanCreatorConfig.getIdentifier())
        .description(environmentPlanCreatorConfig.getDescription())
        .tags(environmentPlanCreatorConfig.getTags())
        .type(environmentPlanCreatorConfig.getType())
        .serviceOverrides(NGVariablesUtils.getMapOfServiceVariables(environmentPlanCreatorConfig.getServiceOverrides()))
        .variables(NGVariablesUtils.getMapOfVariables(environmentPlanCreatorConfig.getVariables()))
        .build();
  }
}
