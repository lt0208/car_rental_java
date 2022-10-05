package com.example.CarRental.Service;

import com.example.CarRental.Exception.ResourceNotFoundException;
import com.example.CarRental.Model.Car;
import com.example.CarRental.Model.Request;
import com.example.CarRental.Model.Status;
import com.example.CarRental.Repository.CustomerRepo;
import com.example.CarRental.Repository.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {
    @Autowired
    private RequestRepo requestRepo;
    @Autowired
    private CustomerRepo customerRepo;

    public void saveRequest(Request request){
        requestRepo.save(request);
    }

    public List<Request> getAllRequests(){
        return requestRepo.findAll();
    }

    public List<Request> getSubmittedRequests(){
        List<Request> allRequests = requestRepo.findAll();
        return  allRequests.stream().filter(each -> each.getStatus() == Status.SUBMITTED).collect(Collectors.toList());
    }

    public List<Request> getRequestsByCustomerId(Long id){
        if (customerRepo.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Customer isn't found with id: "+id);
        }
        List<Request> requestList = requestRepo.findByCustomer_Id(id);
        if (requestList == null){
            throw new ResourceNotFoundException("No request found by customer with id: "+id);
        }
        return requestList;
    }

    public Request getRequestById(Long id){
        return requestRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("can't find the request with id: "+id));
    }

    public boolean checkCarAvailability(Long carId){
        List<Request> requests = requestRepo.findByCar_id(carId);
        for (Request request : requests) {
            if (request.getStatus() == Status.SUBMITTED || request.getStatus() == Status.APPROVED){
                return false;
            }
        }
        return true;
    }

    public boolean checkCarAvailability2(Long carId){
        List<Request> requests = requestRepo.findByCar_id(carId);
        return !requests.stream().anyMatch(request -> (request.getStatus() == Status.SUBMITTED || request.getStatus() == Status.APPROVED));
    }

    public Request changeStatus(Long requestId, int statusId) throws Exception {
        Request request = requestRepo.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("can't find the request with id: "+requestId));
       switch (statusId){
           case 0:
               request.setStatus(Status.SUBMITTED);
               break;
           case 1:
               request.setStatus(Status.APPROVED);
               break;
           case 2:
               request.setStatus(Status.CANCELED);
               break;
           case 3:
               request.setStatus(Status.DENIED);
               break;
           case 4:
               request.setStatus(Status.RETURNED);
               break;
           default:
               throw new Exception("Status value is not valid!");

       }
        //request.setStatus(status);// Alternative: pass enum directly. works in postman but not in React. when passing enum in postman, just give the value, e.g. "APPROVED", skip {}
        return requestRepo.save(request);

    }

}
