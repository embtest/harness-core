/*
 * Copyright 2021 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.pms.sdk.core.resolver;

import static io.harness.annotations.dev.HarnessTeam.CDC;

import io.harness.annotations.dev.OwnedBy;
import io.harness.pms.contracts.ambiance.Ambiance;
import io.harness.pms.contracts.refobjects.RefObject;
import io.harness.pms.sdk.core.data.StepTransput;

import javax.validation.constraints.NotNull;

@OwnedBy(CDC)
public interface Resolver<T extends StepTransput> {
  T resolve(@NotNull Ambiance ambiance, @NotNull RefObject refObject);

  String consume(@NotNull Ambiance ambiance, @NotNull String name, T value, String groupName);
}
