package com.crud.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "developers")
@Getter @Setter
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private int fee;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "developer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Project> projects;

    public Developer() {
    }

    public Developer(long id, String name, int fee) {
        this.id = id;
        this.name = name;
        this.fee = fee;
    }
}
