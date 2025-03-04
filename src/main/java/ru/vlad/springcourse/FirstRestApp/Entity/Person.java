package ru.vlad.springcourse.FirstRestApp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "The name cannot be empty")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters long")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "The surname cannot be empty")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters long")
    private String surname;

    public Person() {
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
