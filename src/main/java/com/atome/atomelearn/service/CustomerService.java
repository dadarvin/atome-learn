package com.atome.atomelearn.service;

import com.atome.atomelearn.dao.CustomerRepository;
import com.atome.atomelearn.exceptions.CustomerException;
import com.atome.atomelearn.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CustomerService {
    @Autowired
    public CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()){
            throw new CustomerException("there is currently no existing customer");
        }

        return customers;
    }

    public Customer getCustomerByID(int id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerException("customer with this id doesn't exist");
        }

        return customer.get();
    }

    public void addCustomer(String name, String birthday, String country) {
        int customerAge;
        try {
            customerAge = currentAge(birthday);
        } catch (ParseException e) {
            throw new CustomerException("Failed to parse birthday format");
        }

        if (name == "") {
            throw new CustomerException("name must not be empty !");
        }
        if (birthday == "") {
            throw new CustomerException("birthday must not be empty !");
        }
        if (country == "") {
            throw new CustomerException("country must not be empty");
        } else if (country.length() > 2){
            throw new CustomerException("country should be filled 2 char only");
        }

        Customer customer = Customer.builder()
                .name(name)
                .birthday(birthday)
                .country(country)
                .age(customerAge).build();
        customerRepository.save(customer);
    }

    public void deleteCustomer(int id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerException("customer with this id doesn't exist");
        }

        customerRepository.deleteById(id);
    }

    private int currentAge(String birthday) throws ParseException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthday, dateFormat);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }
}
