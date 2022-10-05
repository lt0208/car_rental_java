package com.example.CarRental.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String year;
    private String brand;
    private String model;
    private int price;

    public Car(String year, String brand, String model, int price) {
        this.year = year;
        this.brand = brand;
        this.model = model;
        this.price = price;
    }
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Set<Request> requestSet;
}
