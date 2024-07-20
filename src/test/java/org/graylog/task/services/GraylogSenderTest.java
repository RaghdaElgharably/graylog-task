package org.graylog.task.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.graylog.task.models.GelfMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GraylogSenderTest {

  private static final String GRAYLOG_ENDPOINT = "https://api.graylog.com";
  @Mock
  private OkHttpClient mockHttpClient;
  @Mock
  private ObjectMapper mockObjectMapper;
  private GraylogSender graylogSender;

  @BeforeEach
  public void setUp() throws IOException {
    MockitoAnnotations.openMocks(this);
    graylogSender = new GraylogSender(mockHttpClient, mockObjectMapper);
    graylogSender.setGrayLogUrl(GRAYLOG_ENDPOINT);
  }

  /**
   * tests a message is sent successfully to the server
   * @throws Exception
   */
  @Test
  public void testSendMessageSuccess() throws Exception {
    // Mock the Call and Response
    Call mockCall = mock(Call.class);
    Response mockResponse = new Response.Builder()
        .request(new Request.Builder().url(GRAYLOG_ENDPOINT).build())
        .protocol(Protocol.HTTP_1_1)
        .code(200)
        .message("successful")
        .body(ResponseBody.create("", MediaType.get("application/json")))
        .build();
    when(mockObjectMapper.writeValueAsString(any())).thenReturn("{}");
    when(mockCall.execute()).thenReturn(mockResponse);
    when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);

    GelfMessage gelfMessage = new GelfMessage();
    assertTrue(graylogSender.sendGelfMessage(gelfMessage));
  }

  /**
   * tests a message is not sent successfully to the server; the response code is 500
   * @throws Exception
   */
  @Test
  public void testSendGelfMessageFailure() throws IOException {
    // Mock the Call and Response
    Call mockCall = mock(Call.class);
    Response mockResponse = new Response.Builder()
        .request(new Request.Builder().url(GRAYLOG_ENDPOINT).build())
        .protocol(Protocol.HTTP_1_1)
        .code(500)
        .message("Internal Server Error")
        .body(ResponseBody.create("", MediaType.get("application/json")))
        .build();
    when(mockObjectMapper.writeValueAsString(any())).thenReturn("{}");
    when(mockCall.execute()).thenReturn(mockResponse);
    when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);

    GelfMessage gelfMessage = new GelfMessage();
    assertFalse(graylogSender.sendGelfMessage(gelfMessage));
  }

  /**
   * tests an exception is thrown when the request to the server throws an exception.
   * @throws Exception
   */
  @Test
  public void testSendGelfMessageException() throws IOException {

    GelfMessage gelfMessage = new GelfMessage();

    Call mockCall = mock(Call.class);
    when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);
    when(mockCall.execute()).thenThrow(new IOException("Network error"));
    when(mockObjectMapper.writeValueAsString(any())).thenReturn("{}");
    // Mock the httpClient to throw an IOException
    assertThrows(IOException.class, () -> {
      graylogSender.sendGelfMessage(gelfMessage);
    });
  }

}