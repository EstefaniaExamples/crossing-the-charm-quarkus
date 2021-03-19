package org.quarkus.training;

import io.quarkus.arc.config.ConfigProperties;

import javax.validation.constraints.NotEmpty;

@ConfigProperties(prefix = "application")
public class ApplicationProperties {
    @NotEmpty
    public String message;
    public String suffix = "!";
}
