package com.atome.atomelearn.controller;

import com.atome.atomelearn.exceptions.CustomerException;
import com.atome.atomelearn.model.ApiResponseCode;
import com.atome.atomelearn.model.Customer;
import com.atome.atomelearn.model.CustomerResponse;
import com.atome.atomelearn.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    private static Logger logger = LoggerFactory.getLogger(CounterController.class);

    @GetMapping("/get")
    public ResponseEntity<CustomerResponse> getAllCustomer() {
        CustomerResponse response;
        HttpStatus httpStatus;

        logger.info("Received GET Request for getAllCustomer");

        try {
            List<Customer> customers = customerService.getAllCustomer();
            response = new CustomerResponse(ApiResponseCode.SUCCESS.code, customers);
            httpStatus = HttpStatus.OK;

            logger.info("Returning Get Request for getAllCustomer with data: {}", customers);
        } catch (CustomerException e) {
            response = new CustomerResponse(ApiResponseCode.SUCCESS.code, e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return responseReturn(response, httpStatus);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable(value = "id") Integer id
    ) {
        CustomerResponse response;
        HttpStatus httpStatus;

        logger.info("Received GET Request for getCustomer with id {}", id);

        try {
            Customer customer = customerService.getCustomerByID(id);
            response = new CustomerResponse(ApiResponseCode.SUCCESS.code, customer);
            httpStatus = HttpStatus.OK;

            logger.info("Returning Get Request for getCustomer id {} with data: {}", id, customer);
        } catch (CustomerException e) {
            response = new CustomerResponse(ApiResponseCode.BAD_REQUEST.code, e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;

            logger.error("Returning Get Request for getCustomer id {} with error: {}", id, e.getMessage());
        }

        return responseReturn(response, httpStatus);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> addCustomer(
            //TODO: receive string instead in serialized form use RequestEntity<String>
            // Advantage: we can return specific error instead just getting BAD_REQUEST
            @RequestBody Customer request
            ) {
        CustomerResponse response;
        //TODO: httpStatus should be put inside the customerResopnse object instead for more cleaner code
        HttpStatus httpStatus;

        logger.info("Received POST Request for addCustomer with data: {}", request);

        //TODO: validation for the request should be done here

        try {
            //TODO: pass object, after deserializing it instead
            customerService.addCustomer(request.getName(), request.getBirthday(), request.getCountry());
            response = new CustomerResponse(ApiResponseCode.SUCCESS.code, CustomerResponse.OPERATION_SUCCESS);
            httpStatus = HttpStatus.OK;

            logger.info("Returning POST Request for addCustomer with status {}", CustomerResponse.OPERATION_SUCCESS);
        } catch (CustomerException c) {
            response = new CustomerResponse(ApiResponseCode.BAD_REQUEST.code, c.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;

            logger.error("Returning POST Request for addCustomer with status fail {}", c.getMessage());
        }

        return responseReturn(response, httpStatus);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomer(
            @PathVariable(value = "id") int id
    ) {
        CustomerResponse response;
        HttpStatus httpStatus;

        logger.info("Received DELETE Request for deleteCustomer with id: {}", id);

        try {
            customerService.deleteCustomer(id);
            response = new CustomerResponse(ApiResponseCode.SUCCESS.code, CustomerResponse.OPERATION_SUCCESS);
            httpStatus = HttpStatus.OK;

            logger.info("Returning DELETE Request for deleteCustomer with status {}", CustomerResponse.OPERATION_SUCCESS);
        } catch (CustomerException e) {
            response = new CustomerResponse(ApiResponseCode.BAD_REQUEST.code, e.getMessage());
            httpStatus = HttpStatus.OK;

            logger.error("Returning DELETE Request for deleteCustomer with status {}", e.getMessage());
        }

        return responseReturn(response, httpStatus);
    }

    private ResponseEntity<CustomerResponse> responseReturn(CustomerResponse customerResponse, HttpStatus httpStatus) {
        return new ResponseEntity<>(customerResponse, httpStatus);
    }
}
