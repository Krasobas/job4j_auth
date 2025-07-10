package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.dto.RegistrationRequest;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {
  private final PersonRepository personRepository;
  private final PasswordEncoder passwordEncoder;

  public Optional<Person> findByLogin(String login) {
    return personRepository.findByLogin(login);
  }

  public void save(RegistrationRequest request) {
    Person person = new Person();
    person.setLogin(request.username());
    person.setPassword(passwordEncoder.encode(request.password()));
    personRepository.save(person);
  }
}
