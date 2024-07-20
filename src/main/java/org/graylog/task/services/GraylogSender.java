package org.graylog.task.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.graylog.task.models.GelfMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GraylogSender {

  private static final Logger logger = LoggerFactory.getLogger(GraylogSender.class);
  private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
  private final OkHttpClient httpClient;
  private final ObjectMapper objectMapper;
  @Value("${graylog.server.url}")
  private String grayLogUrl; // the url to graylog server


  @Autowired
  public GraylogSender(OkHttpClient httpClient, ObjectMapper objectMapper) {
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
  }

  /**
   * function that sends the logMessage to the graylog server
   *
   * @param logMessage the message that will be written to the graylog server
   * @return if the message was sent successfully to the server or not.
   * @throws IOException if the httpclient call isn't executed or the message isn't serialized to a
   *                     string.
   */
  public boolean sendGelfMessage(GelfMessage logMessage) throws IOException {
    String jsonMessage = objectMapper.writeValueAsString(logMessage);

    RequestBody body = RequestBody.create(jsonMessage, JSON);
    Request request = new Request.Builder()
        .url(grayLogUrl)
        .post(body)
        .build();

    try (Response response = httpClient.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        logger.error(
            "There was an error sending the Gelf message. Status code: {}. Response is {} ",
            response.code(), response);
        return false;
      }
      logger.debug("Message {} sent to Graylog successfully.", logMessage);
    }
    return true;
  }

  public void setGrayLogUrl(String grayLogUrl) {
    this.grayLogUrl = grayLogUrl;
  }
}