package ru.vlad.springcourse.FirstRestApp.util;

public class PersonErrorResponse {
    private String message;
    private long time;

    public PersonErrorResponse(String message, long time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
