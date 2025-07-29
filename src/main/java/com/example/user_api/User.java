package com.example.user_api;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    // Getters & Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){ this.id = id;}

    public String getName(){ return name; }
    public void setName(String name)
    { this.name = name;}

    public String getEmail(){ return email;}
    public void setEmail(String email){
        this.email = email;
    }

    public String getPhoneNumber(){ return phoneNumber;};
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber;}
}
