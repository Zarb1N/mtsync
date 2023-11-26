package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*")
public class PersonController {
    private final PersonRepo repo;

    public PersonController(PersonRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/person")
    public Person findPerson(@RequestParam Long id) {
        return repo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping("/personsByName")
    public List<Person> findPersonsByName(@RequestParam String name) {
        return repo.findAllByName(name);
    }

    @PostMapping("/person")
    public Person savePerson(@RequestBody Person person) {
        return repo.save(person);
    }

    @GetMapping("/person/error")
    public ResponseEntity<Person> getError() {
        throw new RuntimeException("(╯ ͠° ͟ʖ ͡°)╯┻━┻ ");
    }
}
