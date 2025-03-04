package ru.vlad.springcourse.FirstRestApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlad.springcourse.FirstRestApp.Entity.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
