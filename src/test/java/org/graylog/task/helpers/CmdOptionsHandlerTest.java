package org.graylog.task.helpers;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CmdOptionsHandlerTest {

  private final CmdOptionsHandler cmdOptionsHandler = new CmdOptionsHandler();

  /**
   * tests when the correct args sent to assignAndGetOptions
   */
  @Test
  public void testCorrectArgs() {
    String fileName = "logs.txt";
    String path = FileHelper.getFullPath(fileName, CmdOptionsHandlerTest.class);
    String filePath = cmdOptionsHandler.assignAndGetOptions(new String[]{"-f", path});
    assertTrue(filePath.endsWith(fileName));
  }

  /**
   * tests when the incorrect args sent to assignAndGetOptions
   */
  @Test
  public void testIncorrectArgs() {
    String filePath = cmdOptionsHandler.assignAndGetOptions(new String[]{});
    assertNull(filePath);
  }

  /**
   * tests when the correct args sent to assignAndGetOptions but the file isn't correct
   */
  @Test
  public void testIncorrectFilePath() {
    String fileName = "";
    String filePath = cmdOptionsHandler.assignAndGetOptions(new String[]{"-f", fileName});
    assertNull(filePath);
  }

}