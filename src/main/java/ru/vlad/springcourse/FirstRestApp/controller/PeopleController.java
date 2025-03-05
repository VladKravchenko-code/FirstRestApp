package ru.vlad.springcourse.FirstRestApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.vlad.springcourse.FirstRestApp.models.Person;
import ru.vlad.springcourse.FirstRestApp.service.PeopleService;
import ru.vlad.springcourse.FirstRestApp.util.PersonErrorResponse;
import ru.vlad.springcourse.FirstRestApp.util.PersonNotFoundException;
import ru.vlad.springcourse.FirstRestApp.util.PersonNotValidException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PeopleController {

    private PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<Person> allPeople() {
        return peopleService.findAll();
    }

    @GetMapping("/{id}")
    public Person onePerson(@PathVariable int id) {
        return peopleService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> savePerson(@RequestBody @Valid Person person,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError err : errors) {
                errMsg.append(err.getField()).append(" - ")
                        .append(err.getDefaultMessage()).append(";");
            }

            throw new PersonNotValidException(errMsg.toString());
        }
        peopleService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        peopleService.findOne(id);
        peopleService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotFoundException e) {
        PersonErrorResponse err = new PersonErrorResponse("Person not found", System.nanoTime());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotValidException e) {
        PersonErrorResponse err = new PersonErrorResponse(e.getMessage(), System.nanoTime());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
