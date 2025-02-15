/*
 * Copyright 2021 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Shield 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/06/PolyForm-Shield-1.0.0.txt.
 */

package io.harness.timeout.trackers.absolute;

import io.harness.timeout.TimeoutTracker;
import io.harness.timeout.TimeoutTrackerFactory;
import io.harness.timeout.contracts.Dimension;

public class AbsoluteTimeoutTrackerFactory implements TimeoutTrackerFactory<AbsoluteTimeoutParameters> {
  public static final Dimension DIMENSION = Dimension.newBuilder().setType("ABSOLUTE").build();

  @Override
  public TimeoutTracker create(AbsoluteTimeoutParameters parameters) {
    return new AbsoluteTimeoutTracker(parameters.getTimeoutMillis());
  }
}
