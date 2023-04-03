package com.atome.atomelearn.service;

import com.atome.atomelearn.dao.CustomerRepository;
import com.atome.atomelearn.exceptions.CustomerException;
import com.atome.atomelearn.model.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
            customerRedis.expire(id, 60, TimeUnit.SECONDS);
        }

        return customer;
    }

    public void addCustomer(Customer customer) {
        try {
            customer.setAge(calculateAge(customer.getBirthday()));
        } catch (ParseException e) {
            throw new CustomerException("Failed to parse birthday format");
        }

        if (StringUtils.isBlank(customer.getName()) || StringUtils.isEmpty(customer.getName())) {
            throw new CustomerException("name must not be empty !");
        }
        if (StringUtils.isBlank(customer.getBirthday()) || StringUtils.isEmpty(customer.getBirthday())) {
            throw new CustomerException("birthday must not be empty !");
        }
        if (StringUtils.isBlank(customer.getCountry()) || StringUtils.isEmpty(customer.getCountry())) {
            throw new CustomerException("country must not be empty");
        } else if (customer.getCountry().length() > 2){
            throw new CustomerException("country should be filled 2 char only");
        }

        customerRepository.save(customer);
        customerRedis.opsForValue().set(customer.getId(), customer);
        customerRedis.expire(customer.getId(), 60, TimeUnit.SECONDS);
    }

    public void deleteCustomer(int id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerException("customer with this id doesn't exist");
        }

        customerRepository.deleteById(id);
        customerRedis.delete(id);
    }

    public int calculateAge(String birthday) throws ParseException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthday, dateFormat);
        LocalDate currentDate = LocalDate.now();

        int tempAge = Period.between(birthDate, currentDate).getYears();
        LocalDate nextBirthday = birthDate.withYear(currentDate.getYear());

        if (nextBirthday.isAfter(currentDate)) {
            tempAge--;
        }

        return tempAge;
    }
}
