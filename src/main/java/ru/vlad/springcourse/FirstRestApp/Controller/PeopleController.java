package ru.vlad.springcourse.FirstRestApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vlad.springcourse.FirstRestApp.Entity.Person;
import ru.vlad.springcourse.FirstRestApp.Service.PeopleService;

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
        return peopleService.findOne(id).get();
    }
}
