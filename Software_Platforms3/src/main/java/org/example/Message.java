package org.example;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private int number;

    public Message(int num, String mess){
        this.message=mess;
        this.number=num;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
