package com.seed4j;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.AutoApproveWhenEmptyReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.FirstWorkingReporter;

/**
 * ApprovalTests.Java configuration.
 * */
@SuppressWarnings({ "unused", "java:S115" })
public class PackageSettings {

  private static final String ApprovalBaseDirectory = "../resources";
  private static final ApprovalFailureReporter UseReporter = new FirstWorkingReporter(
    new AutoApproveWhenEmptyReporter(),
    new DiffReporter()
  );
}
