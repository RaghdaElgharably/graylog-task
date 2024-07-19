package org.graylog.task.helpers;

import org.junit.Assert;
import org.junit.Test;

public class CmdOptionsHandlerTest {

  private final CmdOptionsHandler cmdOptionsHandler = new CmdOptionsHandler();

  @Test
  public void testCorrectArgs() {
    String fileName = "logs.txt";
    String path = FileHelper.getFullPath(fileName, CmdOptionsHandlerTest.class);
    String filePath = cmdOptionsHandler.assignAndGetOptions(new String[]{"-f", path});
    Assert.assertTrue(filePath.endsWith(fileName));
  }

  @Test
  public void testIncorrectArgs() {
    String filePath = cmdOptionsHandler.assignAndGetOptions(new String[]{});
    Assert.assertNull(filePath);
  }

  @Test
  public void testIncorrectFilePath() {
    String fileName = "";
    String filePath = cmdOptionsHandler.assignAndGetOptions(new String[]{"-f", fileName});
    Assert.assertNull(filePath);
  }

}