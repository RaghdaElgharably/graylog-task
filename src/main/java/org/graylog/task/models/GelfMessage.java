package org.graylog.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GelfMessage extends LogMessage{

  @JsonProperty("host")
  private String host;

  @JsonProperty("short_message")
  private String shortMessage;

  public GelfMessage(String host, String message,
      LogMessage logMessage) {
    super(logMessage);
    this.host = host;
    this.shortMessage = message;
  }

  public GelfMessage() {

  }

  @Override
  public String toString() {
    return "GelfMessage [host=" + host + ", shortMessage=" + shortMessage + ", "
        + "logMessage= "+super.toString();
  }
}