package com.example.CarRental.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Car car;

    public Request(Status status, LocalDate dateCreated, LocalDate startDate, LocalDate endDate, Customer customer, Car car) {
        this.status = status;
        this.dateCreated = dateCreated;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer = customer;
        this.car = car;
    }
}
