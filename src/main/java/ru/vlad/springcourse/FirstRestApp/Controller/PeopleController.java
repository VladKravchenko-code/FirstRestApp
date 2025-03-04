package ru.vlad.springcourse.FirstRestApp.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.vlad.springcourse.FirstRestApp.Entity.Person;
import ru.vlad.springcourse.FirstRestApp.Service.PeopleService;
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

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotFoundException e) {
        PersonErrorResponse err = new PersonErrorResponse("Person not found", System.nanoTime());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<PersonNotValidException> savePerson(@RequestBody @Valid Person person,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError s : bindingResult.getAllErrors()){
                stringBuilder.append(s.getDefaultMessage()).append(";\n");
            }
            throw new PersonNotValidException(stringBuilder.toString());
        }
        peopleService.save(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotValidException e) {
        PersonErrorResponse err = new PersonErrorResponse("Person not valid", System.nanoTime());
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }
}
