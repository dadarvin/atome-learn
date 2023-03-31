package com.atome.atomelearn.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String birthday;
    private int age;
    private String country;
}
