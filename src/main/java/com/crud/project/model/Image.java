package com.crud.project.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Getter @Setter
public class Image {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    public Image() {
    }

    public Image(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
