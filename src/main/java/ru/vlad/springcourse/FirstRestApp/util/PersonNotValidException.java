package ru.vlad.springcourse.FirstRestApp.util;

public class PersonNotValidException extends RuntimeException {

    public PersonNotValidException(String message) {
        System.out.println(message);
    }
}
