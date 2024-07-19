package org.graylog.task.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.graylog.task.models.LogMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GraylogSenderTest {

  private static final String GRAYLOG_ENDPOINT = "https://api.graylog.com";
  @Mock
  private OkHttpClient mockHttpClient;
  @Mock
  private ObjectMapper mockObjectMapper;
  private GraylogSender graylogSender;

  @Before
  public void setUp() throws IOException {
    MockitoAnnotations.openMocks(this);
    graylogSender = new GraylogSender(mockHttpClient, mockObjectMapper, GRAYLOG_ENDPOINT);
  }

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

    LogMessage logMessage = new LogMessage();
    Assert.assertTrue(graylogSender.sendGelfMessage(logMessage));
  }

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

    LogMessage logMessage = new LogMessage();
    Assert.assertFalse(graylogSender.sendGelfMessage(logMessage));
  }

  @Test
  public void testSendGelfMessageException() throws IOException {

    LogMessage logMessage = new LogMessage();

    Call mockCall = mock(Call.class);
    when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);
    when(mockCall.execute()).thenThrow(new IOException("Network error"));
    when(mockObjectMapper.writeValueAsString(any())).thenReturn("{}");
    // Mock the httpClient to throw an IOException
    assertThrows(IOException.class, () -> {
      graylogSender.sendGelfMessage(logMessage);
    });
  }

}