package io.harness.gitsync.common.helper;

import static io.harness.NGCommonEntityConstants.ACCOUNT_KEY;
import static io.harness.annotations.dev.HarnessTeam.PL;
import static io.harness.gitsync.common.helper.GitSyncLogContextHelper.BRANCH_KEY;
import static io.harness.gitsync.common.helper.GitSyncLogContextHelper.GIT_OPERATION_TYPE;
import static io.harness.rule.OwnerRule.BHAVYA;

import static org.assertj.core.api.Assertions.assertThat;

import io.harness.CategoryTest;
import io.harness.ScopeIdentifiers;
import io.harness.annotations.dev.OwnedBy;
import io.harness.category.element.UnitTests;
import io.harness.gitsync.common.beans.GitOperation;
import io.harness.rule.Owner;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.MockitoAnnotations;

@OwnedBy(PL)
public class GitSyncLogContextHelperTest extends CategoryTest {
  private static final String ACCOUNT_IDENTIFIER = "account";
  private static final String ORG_IDENTIFIER = "org";
  private static final String PROJECT_IDENTIFIER = "project";
  private static final String REPO_NAME = "repoName";
  private static final String BRANCH_NAME = "branchName";
  private static final String FILEPATH = "filePath";
  private static ScopeIdentifiers scope;
  private static Map<String, String> logContext = new HashMap<>();

  @Before
  public void setup() {
    scope = ScopeIdentifiers.newBuilder()
                .setAccountIdentifier(ACCOUNT_IDENTIFIER)
                .setOrgIdentifier(ORG_IDENTIFIER)
                .setProjectIdentifier(PROJECT_IDENTIFIER)
                .build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @Owner(developers = BHAVYA)
  @Category(UnitTests.class)
  public void testSetContext() {
    logContext.put(BRANCH_KEY, "branch1");
    logContext = GitSyncLogContextHelper.setContext(
        scope, REPO_NAME, BRANCH_NAME, FILEPATH, GitOperation.CREATE_FILE, logContext);
    assertThat(logContext.get(ACCOUNT_KEY)).isEqualTo(ACCOUNT_IDENTIFIER);
    assertThat(logContext.get(BRANCH_KEY)).isEqualTo("branch1");
    assertThat(logContext.get(GIT_OPERATION_TYPE)).isEqualTo(GitOperation.CREATE_FILE.name());
  }
}
