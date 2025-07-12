package ru.job4j.auth.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public List<PersonResponse> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<PersonResponse> findById(
      @PathVariable @Min(value = 0, message = "Id has to be a positive integer") int id
  ) {
    return new ResponseEntity<PersonResponse>(
        service.findById(id),
        HttpStatus.OK
    );
  }

  @PostMapping("/")
  public ResponseEntity<PersonResponse> create(@RequestBody @Valid RegistrationRequest request) {
    return new ResponseEntity<PersonResponse>(
        service.save(request),
        HttpStatus.CREATED
    );
  }

  @PutMapping("/")
  public ResponseEntity<Void> update(@RequestBody @Valid UpdateRequest request) {
    service.update(request);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(
      @PathVariable @Min(value = 0, message = "Id has to be a positive integer") int id
  ) {
    service.delete(id);
    return ResponseEntity.ok().build();
  }
}
