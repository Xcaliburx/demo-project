package com.crud.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "brokers")
@Getter @Setter
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String phone;

    @Column
    private String location;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    public Broker() {
    }

    public Broker(long id, String name, String description, String phone, String location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.location = location;
    }
}
