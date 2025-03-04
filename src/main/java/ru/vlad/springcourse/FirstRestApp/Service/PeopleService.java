package ru.vlad.springcourse.FirstRestApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlad.springcourse.FirstRestApp.Entity.Person;
import ru.vlad.springcourse.FirstRestApp.Repository.PeopleRepository;
import ru.vlad.springcourse.FirstRestApp.util.PersonNotFoundException;

import java.util.List;

@Service
public class PeopleService {

    private PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }
}
