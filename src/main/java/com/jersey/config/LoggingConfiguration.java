//package com.jersey.config;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.dropwizard.Configuration;
//import io.dropwizard.logging.ConsoleAppenderFactory;
//import io.dropwizard.logging.DefaultLoggingFactory;
//import io.dropwizard.logging.Levels;
//import io.dropwizard.logging.LoggingFactory;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotNull;
//
//public class LoggingConfiguration {
//
//  @Valid
//  @NotNull
//  @JsonProperty
//  private String level;
//
//  @Valid
//  @NotNull
//  @JsonProperty
//  private ConsoleAppenderFactory console;
//
//  @JsonProperty("level")
//  public String getLevel() {
//    return level;
//  }
//
//  @JsonProperty("level")
//  public void setLevel(String level) {
//    this.level = level;
//  }
//
//  @JsonProperty("console")
//  public ConsoleAppenderFactory getConsole() {
//    return console;
//  }
//
//  @JsonProperty("console")
//  public void setConsole(ConsoleAppenderFactory console) {
//    this.console = console;
//  }
//
//  public LoggingFactory buildLoggingFactory() {
//    DefaultLoggingFactory loggingFactory = new DefaultLoggingFactory();
//    loggingFactory.setLevel(Levels.byName(level));
//    loggingFactory.setConsoleAppender(console);
//    return loggingFactory;
//  }
//
//  public void configureLogging(Configuration configuration) {
//    configuration.setLoggingFactory(buildLoggingFactory());
//  }
//}
