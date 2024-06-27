package com.example.boot_azure.controller;


import com.azure.cosmos.models.PartitionKey;
import com.example.boot_azure.dto.CustomerCrudResponse;
import com.example.boot_azure.model.Customer;
import com.example.boot_azure.repository.CustomerDBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerDBRepository customerDBRepository;

    public CustomerController(CustomerDBRepository customerDBRepository){
        this.customerDBRepository = customerDBRepository;
    }

    @PostMapping
    public ResponseEntity<CustomerCrudResponse> createNewCustomer(@RequestBody Customer c) {

        c = customerDBRepository.save(c);
        CustomerCrudResponse customerCrudResponse = new CustomerCrudResponse();
        customerCrudResponse.setMessage("New customer created successfully with the ID: " + c.getId());
        customerCrudResponse.setStatusCode("00");
        return new ResponseEntity<CustomerCrudResponse>(customerCrudResponse, HttpStatus.CREATED);
    }

    //Update existing Customer
    @PutMapping("/{id}")
    public ResponseEntity<String> updateExistingCustomer(@PathVariable String id, @RequestBody Customer c) {

        customerDBRepository.save(c);
        return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }


    //get customer details
    @GetMapping("/{id}")
    public ResponseEntity<List<Customer>> getCustomers(@PathVariable String id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("ContentType", "application/json");
        List<Customer> customerList = new ArrayList<>();

        logger.info("Id is present in the GET request");
        List<Optional<Customer>> optionaCustomerList = Collections.singletonList(customerDBRepository.findById(id));
        if (!(optionaCustomerList.get(0).isEmpty())) {
            optionaCustomerList.stream().forEach(c -> c.ifPresent(customer -> customerList.add(customer)));
            return new ResponseEntity<List<Customer>>(customerList, responseHeaders, HttpStatus.OK);
        }


        return new ResponseEntity<List<Customer>>(customerList, responseHeaders, HttpStatus.NOT_FOUND);

    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("ContentType", "application/json");
        List<Customer> customerList = new ArrayList<>();

        logger.info("Id is not present in the GET request");
        customerList = customerDBRepository.getAllCustomers();
        return new ResponseEntity<List<Customer>>(customerList, responseHeaders, HttpStatus.OK);

    }

    //delete the customer of a particular id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExistingCustomer(@PathVariable String id) {
        Optional<Customer> customer = customerDBRepository.findById(id);
        customerDBRepository.deleteById(id, new PartitionKey(customer.get().getLastName()));
        return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }

}
