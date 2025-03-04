package ru.vlad.springcourse.FirstRestApp.Service;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import ru.vlad.springcourse.FirstRestApp.Entity.Person;
import ru.vlad.springcourse.FirstRestApp.Repository.PeopleRepository;
import ru.vlad.springcourse.FirstRestApp.util.PersonNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
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

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }
}
