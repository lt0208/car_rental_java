package com.example.CarRental.Controller;

import com.example.CarRental.Model.Request;
import com.example.CarRental.Model.Status;
import com.example.CarRental.Service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @GetMapping("/all")
    public List<Request> getAllRequests(){
        return requestService.getAllRequests();
    }

    @GetMapping("/submitted")
    public List<Request> getAllSubmittedRequests(){
        return requestService.getSubmittedRequests();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity getRequestsOfCustomer(@PathVariable Long id){
        try{
            return ResponseEntity.ok(requestService.getRequestsByCustomerId(id));
        } catch(Exception e){
            System.out.println("----------------");
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(e.getMessage());
        }
    }

    @PostMapping("/make")
    public ResponseEntity<Request> makeRequest(@RequestBody Request request){
        requestService.saveRequest(request);
        return ResponseEntity.ok(request);
    }

    @PutMapping("/handle/{requestId}/{statusId}")
    public ResponseEntity handleRequest(@PathVariable Long requestId, @PathVariable int statusId){
       try {
           return ResponseEntity.ok(requestService.changeStatus(requestId,statusId));

       }catch(Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
    }
}
