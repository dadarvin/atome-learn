package com.atome.atomelearn.controller;

import com.atome.atomelearn.exceptions.CustomerException;
import com.atome.atomelearn.model.Customer;
import com.atome.atomelearn.model.CustomerResponse;
import com.atome.atomelearn.service.CustomerService;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
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
        logger.info("Received GET Request for getAllCustomer");

        try {
            List<Customer> customers = customerService.getAllCustomer();
            response = new CustomerResponse(CustomerResponse.OPERATION_SUCCESS, customers);
            response.httpStatus = HttpStatus.OK;

            logger.info("Returning Get Request for getAllCustomer with data: {}", customers);
        } catch (CustomerException e) {
            response = new CustomerResponse(CustomerResponse.OPERATION_FAILED, e.getMessage());
            response.httpStatus = HttpStatus.BAD_REQUEST;
        }

        return responseReturn(response);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable(value = "id") Integer id
    ) {
        CustomerResponse response;
        logger.info("Received GET Request for getCustomer with id {}", id);

        try {
            Customer customer = customerService.getCustomerByID(id);
            response = new CustomerResponse(CustomerResponse.OPERATION_SUCCESS, customer);
            response.httpStatus = HttpStatus.OK;

            logger.info("Returning Get Request for getCustomer id {} with data: {}", id, customer);
        } catch (CustomerException e) {
            response = new CustomerResponse(CustomerResponse.OPERATION_FAILED, e.getMessage());
            response.httpStatus = HttpStatus.BAD_REQUEST;

            logger.error("Returning Get Request for getCustomer id {} with error: {}", id, e.getMessage());
        }

        return responseReturn(response);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> addCustomer(
                RequestEntity<String> request
            ) {
        CustomerResponse response;

        // Request validation
        if (StringUtils.isBlank(request.getBody())) {
            return malformedRequestErrorResponse();
        }

        if (!malformedJsonRequest(request.getBody())) {
            return malformedRequestErrorResponse();
        }

        Customer customer = convertStringToObject(request.getBody(), Customer.class);

        logger.info("Received POST Request for addCustomer with data: {}", request);

        try {
            customerService.addCustomer(customer);

            response = new CustomerResponse(CustomerResponse.OPERATION_SUCCESS, "success add new customer");
            response.httpStatus = HttpStatus.OK;

            logger.info("Returning POST Request for addCustomer with status {}", CustomerResponse.OPERATION_SUCCESS);
        } catch (CustomerException c) {
            response = new CustomerResponse(CustomerResponse.OPERATION_FAILED, c.getMessage());
            response.httpStatus = HttpStatus.BAD_REQUEST;

            logger.error("Returning POST Request for addCustomer with status fail {}", c.getMessage());
        }

        return responseReturn(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomer(
            @PathVariable(value = "id") int id
    ) {
        CustomerResponse response;
        logger.info("Received DELETE Request for deleteCustomer with id: {}", id);

        try {
            customerService.deleteCustomer(id);
            response = new CustomerResponse(CustomerResponse.OPERATION_SUCCESS, "customer successfully deleted");
            response.httpStatus = HttpStatus.OK;

            logger.info("Returning DELETE Request for deleteCustomer with status {}", CustomerResponse.OPERATION_SUCCESS);
        } catch (CustomerException e) {
            response = new CustomerResponse(CustomerResponse.OPERATION_FAILED, e.getMessage());
            response.httpStatus = HttpStatus.OK;

            logger.error("Returning DELETE Request for deleteCustomer with status {}", e.getMessage());
        }

        return responseReturn(response);
    }

    protected <T> T convertStringToObject(String request, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();

        T returnObject = null;
        try {
            returnObject = objectMapper.readValue(request, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnObject;
    }

    private boolean malformedJsonRequest(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readTree(json);
        } catch (JacksonException e) {
            return false;
        }

        return true;
    }

    private ResponseEntity<CustomerResponse> malformedRequestErrorResponse() throws CustomerException {
        CustomerResponse response = new CustomerResponse(HttpStatus.BAD_REQUEST.name(), "fail to verify body");
        response.httpStatus = HttpStatus.BAD_REQUEST;

        return responseReturn(response);
    }

    private ResponseEntity<CustomerResponse> responseReturn(CustomerResponse customerResponse) {
        return new ResponseEntity<>(customerResponse, customerResponse.httpStatus);
    }
}
