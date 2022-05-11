/*
 * Copyright 2021 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Shield 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/06/PolyForm-Shield-1.0.0.txt.
 */

package io.harness.audit.beans;

import io.harness.annotations.dev.OwnedBy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static io.harness.annotations.dev.HarnessTeam.PL;

@OwnedBy(PL)
@Data
@Builder
@Schema(name = "AuditSettings", description = "This has the AuditSettings details defined in Harness.")
public class AuditSettingsDTO {
  @Schema(description = "Retention time of the Audits in terms of months") @NotNull int retentionPeriodInMonths;
}
