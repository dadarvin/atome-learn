package com.atome.atomelearn.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {

    public static final String OPERATION_SUCCESS = "success";

    public int code;
    public Customer customer;
    public List<Customer> customers;
    public String status;

    public CustomerResponse(int code, Customer customer) {
        this.code = code;
        this.customer = customer;
        this.status = status;
    }

    public CustomerResponse(int code, List<Customer> customers) {
        this.code = code;
        this.customers = customers;
        this.status = status;
    }

    public CustomerResponse(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
