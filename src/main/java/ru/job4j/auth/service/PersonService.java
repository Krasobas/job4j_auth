package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.dto.PersonResponse;
import ru.job4j.auth.dto.RegistrationRequest;
import ru.job4j.auth.dto.UpdateRequest;
import ru.job4j.auth.repository.PersonRepository;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class PersonService {
  private final PersonRepository repository;
  private final PasswordEncoder passwordEncoder;

  public Optional<Person> findByLogin(String login) {
    return repository.findByLogin(login);
  }

  public PersonResponse save(RegistrationRequest request) {
    if (findByLogin(request.username()).isPresent()) {
      throw new IllegalArgumentException("Login is already taken!");
    }
    Person person = new Person();
    person.setLogin(request.username());
    person.setPassword(passwordEncoder.encode(request.password()));
    person = repository.save(person);
    return new PersonResponse(person.getId(), person.getLogin());
  }

  public PersonResponse update(UpdateRequest request) {
    Person person = repository.findById(request.id())
        .orElseThrow(() -> new NoSuchElementException("Profile not found."));
    person.setLogin(request.username());
    person.setPassword(passwordEncoder.encode(request.password()));
    person = repository.save(person);
    return new PersonResponse(person.getId(), person.getLogin());
  }

  public void delete(int id) {
    Person person =  repository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Profile not found."));
    repository.delete(person);
  }

  public PersonResponse findById(int id) {
    return repository.findById(id)
        .map(person -> new PersonResponse(person.getId(), person.getLogin()))
        .orElseThrow(() -> new NoSuchElementException("Profile not found."));
  }

  public List<PersonResponse> findAll() {
    Spliterator<Person> result = repository.findAll()
        .spliterator();
    return StreamSupport.stream(result, false)
        .map(person -> new PersonResponse(person.getId(), person.getLogin()))
        .toList();
  }
}
