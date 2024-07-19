package org.graylog.task.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import junit.framework.TestCase;
import org.graylog.task.models.LogMessage;

public class JsonLogParserTest extends TestCase {

  /**
   * Checks that a json object is correctly parsed
   *
   * @throws JsonProcessingException
   */
  public void testCorrectJsonLogParsing() throws JsonProcessingException {
    String clientDeviceType = "mobile";
    String clientIP = "192.168.239.245";
    String clientIPClass = "noRecord";
    int clientStatus = 200;
    int clientRequestBytes = 632;
    String clientRequestReferer = "192.168.239.245";
    String clientRequestURI = "search";
    String clientRequestUserAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/600.7.12 (KHTML, like Gecko) Version/8.0.7 Safari/600.7.12";
    int clientSrcPort = 264;
    String edgeServerIP = "10.0.130.188";
    int edgeStartTimestamp = 1576929197;
    String destinationIP = "172.16.122.29";
    int originResponseBytes = 616;
    int originResponseTime = 435000000;
    String exampleLog =
        "{\"ClientDeviceType\": \"" + clientDeviceType + "\",\"ClientIP\": \"" + clientIP + "\","
            + "\"ClientIPClass\": \"" + clientIPClass + "\",\"ClientStatus\": " + clientStatus
            + ", \"ClientRequestBytes\": " + clientRequestBytes + ","
            + "\"ClientRequestReferer\": \"" + clientRequestReferer + "\",\"ClientRequestURI\": \""
            + clientRequestURI + "\","
            + "\"ClientRequestUserAgent\": \"" + clientRequestUserAgent + "\","
            + "\"ClientSrcPort\":" + clientSrcPort + ",\"EdgeServerIP\": \"" + edgeServerIP
            + "\",\"EdgeStartTimestamp\": " + edgeStartTimestamp + ","
            + "\"DestinationIP\": \"" + destinationIP + "\",\"OriginResponseBytes\":  "
            + originResponseBytes + ",\"OriginResponseTime\": " + originResponseTime + "}";
    LogMessage logMessage = JsonLogParser.parseJson(exampleLog, LogMessage.class);
    LogMessage actualLogMessage = new LogMessage(clientDeviceType, clientIP, clientIPClass,
        clientStatus,
        clientRequestBytes, clientRequestReferer, clientRequestURI, clientRequestUserAgent,
        clientSrcPort,
        edgeServerIP, edgeStartTimestamp, destinationIP, originResponseBytes, originResponseTime);
    assertNotNull(logMessage);
    assertEquals(actualLogMessage, logMessage);
  }

}