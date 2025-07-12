package ru.job4j.auth.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PatchRequest(
    @NotNull(message = "Id is required")
    @Min(value = 1, message = "Id must be a positive integer")
    Integer id,
    String username,
    String password
) { }
