package ru.vlad.springcourse.FirstRestApp.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.vlad.springcourse.FirstRestApp.dto.PersonDTO;
import ru.vlad.springcourse.FirstRestApp.models.Person;
import ru.vlad.springcourse.FirstRestApp.service.PeopleService;
import ru.vlad.springcourse.FirstRestApp.util.PersonErrorResponse;
import ru.vlad.springcourse.FirstRestApp.util.PersonNotFoundException;
import ru.vlad.springcourse.FirstRestApp.util.PersonNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonDTO> allPeople() {
        return peopleService.findAll().stream().map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO onePerson(@PathVariable int id) {
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> savePerson(@RequestBody @Valid PersonDTO personDTO,
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
        peopleService.save(convertToPerson(personDTO));
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

    private Person convertToPerson(PersonDTO personDto) {
        return modelMapper.map(personDto, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

}
