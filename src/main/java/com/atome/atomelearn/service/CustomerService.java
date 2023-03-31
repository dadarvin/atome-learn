package com.atome.atomelearn.service;

import com.atome.atomelearn.dao.CustomerRepository;
import com.atome.atomelearn.exceptions.CustomerException;
import com.atome.atomelearn.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CustomerService {
    @Autowired
    public CustomerRepository customerRepository;

    @Autowired
    private RedisTemplate<Integer, Customer> customerRedis;

    public List<Customer> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()){
            throw new CustomerException("there is currently no existing customer");
        }

        return customers;
    }

    public Customer getCustomerByID(int id) {
        // TODO: add logging whether data from Cache(Redis) or database
        Customer customer = customerRedis.opsForValue().get(id);
        if (customer == null) {
            Optional<Customer> optCustomer = customerRepository.findById(id);
            if (optCustomer.isEmpty()) {
                throw new CustomerException("customer with this id doesn't exist");
            }

            customer = optCustomer.get();
            customerRedis.opsForValue().set(id, customer);
        }

        return customer;
    }

    public void addCustomer(String name, String birthday, String country) {
        int customerAge;
        try {
            customerAge = calculateAge(birthday);
        } catch (ParseException e) {
            throw new CustomerException("Failed to parse birthday format");
        }

        if (name == "") {
            throw new CustomerException("name must not be empty !");
        }
        // TODO: implement apache common utils, to check if null or empty (avoid null pointer exception)
        if (birthday == "") {
            throw new CustomerException("birthday must not be empty !");
        }
        if (country.isEmpty()) {
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
        //TODO: implement TTL
        customerRedis.opsForValue().set(customer.getId(), customer);
    }

    public void deleteCustomer(int id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerException("customer with this id doesn't exist");
        }

        // TODO: data in redis not deleted yet, need to check
        customerRepository.deleteById(id);
        customerRedis.delete(id);
    }

    private int calculateAge(String birthday) throws ParseException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthday, dateFormat);
        LocalDate currentDate = LocalDate.now();

        //TODO: need to improve age, user might have 1 age older, difference in days might help

        return Period.between(birthDate, currentDate).getYears();
    }
}
