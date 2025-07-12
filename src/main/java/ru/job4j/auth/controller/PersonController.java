package ru.job4j.auth.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.dto.PersonResponse;
import ru.job4j.auth.dto.RegistrationRequest;
import ru.job4j.auth.dto.UpdateRequest;
import ru.job4j.auth.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@AllArgsConstructor
public class PersonController {
  private final PersonService service;

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public List<PersonResponse> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PersonResponse findById(
      @PathVariable @Min(value = 0, message = "Id has to be a positive integer") int id
  ) {
    return service.findById(id);
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public PersonResponse create(@RequestBody @Valid RegistrationRequest request) {
    return service.save(request);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public void update(@RequestBody @Valid UpdateRequest request) {
    service.update(request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable @Min(value = 0, message = "Id has to be a positive integer") int id
  ) {
    service.delete(id);
  }
}
