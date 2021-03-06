/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.api.tests;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;

public class ProjectTestsImplTest {

  private ProjectTestsImpl projectTests;

  @Before
  public void before(){
    projectTests = new ProjectTestsImpl();
  }

  @Test
  public void should_add_new_test() throws Exception {
    org.sonar.api.tests.Test test = new org.sonar.api.tests.Test("test")
        .setStatus("ok")
        .setMessage("message")
        .setStackTrace("stacktrace")
        .setDurationMilliseconds(10)
    ;
    projectTests.addTest("key", test);
    assertThat(projectTests.getFileTests()).hasSize(1);
    assertThat(projectTests.getFileTests().get(0)).isEqualTo(new FileTest("key"));
    assertThat(projectTests.getFileTests().get(0).getTests()).hasSize(1);
    assertThat(projectTests.getFileTests().get(0).getTests().get(0)).isEqualTo(test);
  }

  @Test
  public void should_add_new_test_on_existing_file_test() throws Exception {
    org.sonar.api.tests.Test test = new org.sonar.api.tests.Test("test").setStatus("ok");
    projectTests.addTest("key", test);

    org.sonar.api.tests.Test test2 = new org.sonar.api.tests.Test("test2").setStatus("ok");
    projectTests.addTest("key", test2);

    assertThat(projectTests.getFileTests()).hasSize(1);
    assertThat(projectTests.getFileTests().get(0).getTests()).hasSize(2);
  }

  @Test
  public void should_add_coverage_info() throws Exception {
    org.sonar.api.tests.Test test = new org.sonar.api.tests.Test("test").setStatus("ok");
    projectTests.addTest("key", test);

    projectTests.cover("key", "test", "mainFile", newArrayList(1, 2));
  }
}
