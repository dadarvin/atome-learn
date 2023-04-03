package com.atome.atomelearn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {

    public static final String OPERATION_SUCCESS = "success";
    public static final String OPERATION_FAILED = "failed";

    public Customer customer;
    public List<Customer> customers;
    public String status;
    public String message;
    @JsonIgnore
    public HttpStatus httpStatus;

    public CustomerResponse(String status, Customer customer) {
        this.status = status;
        this.customer = customer;
    }

    public CustomerResponse(String status, List<Customer> customers) {
        this.status = status;
        this.customers = customers;
    }

    public CustomerResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
