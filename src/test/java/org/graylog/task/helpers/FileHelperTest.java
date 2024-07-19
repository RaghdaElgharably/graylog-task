package org.graylog.task.helpers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import org.junit.Test;

public class FileHelperTest {

  @Test
  public void testCorrectlyReadingFile() throws IOException {
    String path = FileHelper.getFullPath("logs.txt", FileHelperTest.class);

    List<String> lines = FileHelper.readFile(path);
    assertNotNull(lines);
    assertEquals(4, lines.size());
    assertEquals("{\"person\": x, \"age\":20, \"Location\":DE}", lines.get(0));
  }


}