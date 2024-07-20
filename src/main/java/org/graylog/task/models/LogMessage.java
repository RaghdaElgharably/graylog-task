package org.graylog.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class LogMessage {

  @JsonProperty("ClientDeviceType")
  private String clientDeviceType;
  @JsonProperty("ClientIP")
  private String clientIP;
  @JsonProperty("ClientIPClass")
  private String clientIPClass;
  @JsonProperty("ClientStatus")
  private Integer clientStatus;
  @JsonProperty("ClientRequestBytes")
  private Integer clientRequestBytes;
  @JsonProperty("ClientRequestReferer")
  private String clientRequestReferer;
  @JsonProperty("ClientRequestURI")
  private String clientRequestURI;
  @JsonProperty("ClientRequestUserAgent")
  private String clientRequestUserAgent;
  @JsonProperty("ClientSrcPort")
  private Integer clientSrcPort;
  @JsonProperty("EdgeServerIP")
  private String edgeServerIP;
  @JsonProperty("EdgeStartTimestamp")
  private Integer edgeStartTimestamp;
  @JsonProperty("DestinationIP")
  private String destinationIP;
  @JsonProperty("OriginResponseBytes")
  private Integer originResponseBytes;
  @JsonProperty("OriginResponseTime")
  private Integer originResponseTime;

  public LogMessage(LogMessage logMessage) {
    this.clientDeviceType = logMessage.clientDeviceType;
    this.clientIP = logMessage.clientIP;
    this.clientIPClass = logMessage.clientIPClass;
    this.clientStatus = logMessage.clientStatus;
    this.clientRequestBytes = logMessage.clientRequestBytes;
    this.clientRequestReferer = logMessage.clientRequestReferer;
    this.clientRequestURI = logMessage.clientRequestURI;
    this.clientRequestUserAgent = logMessage.clientRequestUserAgent;
    this.clientSrcPort = logMessage.clientSrcPort;
    this.edgeServerIP = logMessage.edgeServerIP;
    this.edgeStartTimestamp = logMessage.edgeStartTimestamp;
    this.destinationIP = logMessage.destinationIP;
    this.originResponseBytes = logMessage.originResponseBytes;
    this.originResponseTime = logMessage.originResponseTime;
  }

  public LogMessage() {

  }

  public LogMessage(String clientDeviceType, String clientIP, String clientIPClass,
      Integer clientStatus, Integer clientRequestBytes, String clientRequestReferer,
      String clientRequestURI, String clientRequestUserAgent, Integer clientSrcPort,
      String edgeServerIP, Integer edgeStartTimestamp, String destinationIP,
      Integer originResponseBytes, Integer originResponseTime) {
    this.clientDeviceType = clientDeviceType;
    this.clientIP = clientIP;
    this.clientIPClass = clientIPClass;
    this.clientStatus = clientStatus;
    this.clientRequestBytes = clientRequestBytes;
    this.clientRequestReferer = clientRequestReferer;
    this.clientRequestURI = clientRequestURI;
    this.clientRequestUserAgent = clientRequestUserAgent;
    this.clientSrcPort = clientSrcPort;
    this.edgeServerIP = edgeServerIP;
    this.edgeStartTimestamp = edgeStartTimestamp;
    this.destinationIP = destinationIP;
    this.originResponseBytes = originResponseBytes;
    this.originResponseTime = originResponseTime;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    LogMessage logMessage = (LogMessage) obj;
    return Objects.equals(clientDeviceType, logMessage.clientDeviceType) &&
        Objects.equals(clientIP, logMessage.clientIP) && Objects.equals(clientIPClass,
        logMessage.clientIPClass) &&
        Objects.equals(clientStatus, logMessage.clientStatus) && Objects.equals(clientRequestBytes,
        logMessage.clientRequestBytes) &&
        Objects.equals(clientRequestURI, logMessage.clientRequestURI) && Objects.equals(
        clientRequestUserAgent, logMessage.clientRequestUserAgent) &&
        Objects.equals(clientSrcPort, logMessage.clientSrcPort) && Objects.equals(edgeServerIP,
        logMessage.edgeServerIP) &&
        Objects.equals(edgeStartTimestamp, logMessage.edgeStartTimestamp) && Objects.equals(
        destinationIP, logMessage.destinationIP) &&
        Objects.equals(originResponseTime, logMessage.originResponseTime) && Objects.equals(
        originResponseBytes, logMessage.originResponseBytes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientDeviceType, clientIP, clientIPClass, clientStatus, clientRequestBytes,
        clientRequestReferer, clientRequestURI, clientRequestUserAgent, clientSrcPort, edgeServerIP,
        edgeStartTimestamp, destinationIP, originResponseBytes, originResponseTime);
  }

  @Override
  public String toString() {
    return "[clientDeviceType= "+ clientDeviceType+" , clientIP= "+clientIP+" , clientIPClass= "+ clientIPClass+ " , "
        + "clientStatus= "+ clientStatus+" , clientRequestBytes= "+ clientRequestBytes+" , clientRequestReferer= "
        + " "+clientRequestReferer+" , clientRequestURI= "+clientRequestURI+" , clientRequestUserAgent= "
        + " "+clientRequestUserAgent+" , clientSrcPort= "+clientSrcPort+" , edgeServerIP= "+edgeServerIP+" , "
        + "edgeStartTimestamp= "+edgeStartTimestamp+" , destinationIP= "+destinationIP+" , originResponseBytes= "+
        originResponseBytes+" , originResponseTime= "+originResponseTime+"]";
  }
}
