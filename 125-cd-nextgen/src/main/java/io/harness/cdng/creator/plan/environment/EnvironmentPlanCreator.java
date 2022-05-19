/*
 * Copyright 2021 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Shield 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/06/PolyForm-Shield-1.0.0.txt.
 */

package io.harness.cdng.creator.plan.environment;

import io.harness.annotations.dev.HarnessTeam;
import io.harness.annotations.dev.OwnedBy;
import io.harness.cdng.environment.yaml.EnvironmentPlanCreatorConfig;
import io.harness.cdng.infra.steps.InfraSectionStepParameters;
import io.harness.cdng.visitor.YamlTypes;
import io.harness.data.structure.UUIDGenerator;
import io.harness.ng.core.environment.beans.EnvironmentType;
import io.harness.pms.sdk.core.adviser.success.OnSuccessAdviserParameters;
import io.harness.pms.sdk.core.plan.PlanNode;
import io.harness.pms.sdk.core.plan.creation.beans.PlanCreationContext;
import io.harness.pms.sdk.core.plan.creation.beans.PlanCreationResponse;
import io.harness.pms.sdk.core.plan.creation.creators.PartialPlanCreator;
import io.harness.serializer.KryoSerializer;

import com.google.inject.Inject;
import com.google.protobuf.ByteString;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@OwnedBy(HarnessTeam.CDC)
public class EnvironmentPlanCreator implements PartialPlanCreator<EnvironmentPlanCreatorConfig> {
  @Inject KryoSerializer kryoSerializer;

  @Override
  public Class<EnvironmentPlanCreatorConfig> getFieldClass() {
    return EnvironmentPlanCreatorConfig.class;
  }

  @Override
  public Map<String, Set<String>> getSupportedTypes() {
    return Collections.singletonMap(YamlTypes.ENVIRONMENT_YAML,
        new HashSet<>(Arrays.asList(EnvironmentType.PreProduction.name(), EnvironmentType.Production.name())));
  }

  @Override
  public PlanCreationResponse createPlanForField(
      PlanCreationContext ctx, EnvironmentPlanCreatorConfig environmentPlanCreatorConfig) {
    /*
    EnvironmentPlanCreator is dependent on infraSectionStepParameters and serviceSpecNodeUuid which is used as advisor
     */
    InfraSectionStepParameters infraSectionStepParameters =
        (InfraSectionStepParameters) kryoSerializer.asInflatedObject(
            ctx.getDependency().getMetadataMap().get(YamlTypes.INFRASTRUCTURE_STEP_PARAMETERS).toByteArray());

    String serviceSpecNodeUuid = (String) kryoSerializer.asInflatedObject(
        ctx.getDependency().getMetadataMap().get(YamlTypes.SERVICE_SPEC).toByteArray());

    ByteString advisorParameters = ByteString.copyFrom(
        kryoSerializer.asBytes(OnSuccessAdviserParameters.builder().nextNodeId(serviceSpecNodeUuid).build()));
    PlanNode planNode = EnvironmentPlanCreatorHelper.getPlanNode(
        UUIDGenerator.generateUuid(), infraSectionStepParameters, advisorParameters);
    return PlanCreationResponse.builder().planNode(planNode).build();
  }
}