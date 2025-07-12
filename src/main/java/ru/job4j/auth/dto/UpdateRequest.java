package ru.job4j.auth.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateRequest(
    @NotNull(message = "Id is required")
    @Min(value = 1, message = "Id must be a positive integer")
    Integer id,
    @NotNull(message = "User name could not be null or empty")
    String username,
    @NotNull(message = "Password could not be null or empty")
    String password
) { }
