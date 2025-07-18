package ru.job4j.auth.dto;

import jakarta.validation.constraints.NotNull;

public record RegistrationRequest(
    @NotNull(message = "User name could not be null or empty")
    String username,
    @NotNull(message = "Password could not be null or empty")
    String password
) { }
