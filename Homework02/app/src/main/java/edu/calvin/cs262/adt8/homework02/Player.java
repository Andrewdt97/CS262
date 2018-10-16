package edu.calvin.cs262.adt8.homework02;

public class Player {
    public int id;
    public String email;
    public String name;

    public Player() {
        this.id = -1;
        this.email = "";
        this.name = "";
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
