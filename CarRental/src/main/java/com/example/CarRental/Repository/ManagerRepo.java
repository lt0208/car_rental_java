package com.example.CarRental.Repository;

import com.example.CarRental.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepo extends JpaRepository<Customer, Long> {
}
