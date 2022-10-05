package com.example.CarRental.Controller;
import com.example.CarRental.Model.Customer;
import com.example.CarRental.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity getCustomerById(@PathVariable Long id){
        try{Customer customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);}
        catch (Exception e) {
            return  ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetail){
        try{
            Customer customer = customerService.getCustomerById(id);
            customer.setFirstName(customerDetail.getFirstName());
            customer.setLastName(customerDetail.getLastName());
            customer.setEmail(customerDetail.getEmail());
            customerService.saveCustomer(customer);
            return ResponseEntity.ok(customer);
        }catch (Exception e) {
            return  ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }
    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        customerService.saveCustomer(customer);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCar(@PathVariable Long id){
        try{
            customerService.deleteCustomerById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return  ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
