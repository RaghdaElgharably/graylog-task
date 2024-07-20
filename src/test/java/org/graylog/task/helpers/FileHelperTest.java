package org.graylog.task.helpers;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileHelperTest {

  /**
   * tests reading a file correctly.
   * @throws IOException
   */
  @Test
  public void testCorrectlyReadingFile() throws IOException {
    String path = FileHelper.getFullPath("logs.txt", FileHelperTest.class);

    List<String> lines = FileHelper.readFile(path);
    assertNotNull(lines);
    assertEquals(4, lines.size());
    assertEquals("{\"person\": x, \"age\":20, \"Location\":DE}", lines.get(0));
  }


}