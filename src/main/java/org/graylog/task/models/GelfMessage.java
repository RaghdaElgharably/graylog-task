package org.graylog.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GelfMessage extends LogMessage {

  @JsonProperty("host")
  private String host;

  @JsonProperty("short_message")
  private String shortMessage;

  @JsonProperty("timestamp")
  private double timestamp; // timestamp should be a double

  @JsonProperty("level")
  private int level;


  public GelfMessage(String host, String message, double timestamp, int level,
      LogMessage logMessage) {
    super(logMessage);
    this.host = host;
    this.timestamp = timestamp;
    this.level = level;
    this.shortMessage = message;
  }

  public GelfMessage() {

  }
}
