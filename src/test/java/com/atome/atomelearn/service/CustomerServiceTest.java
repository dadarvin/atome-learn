package com.atome.atomelearn.service;

import com.atome.atomelearn.dao.CustomerRepository;
import com.atome.atomelearn.exceptions.CustomerException;
import com.atome.atomelearn.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

//    @Test
//    void getAllCustomer_validRequest_success() {
//        Customer customer = Customer.builder()
//                .name("test")
//                .birthday("2001-11-03")
//                .age(20)
//                .country("ID").build();
//        List<Customer> customers = new ArrayList<>();
//        customers.add(customer);
//
//        when(customerRepository.findAll()).thenReturn(List.of(customer));
//        assertEquals(customers, customerService.getAllCustomer());
//    }
//
//    @Test
//    void getCustomerByID_validRequest_success() {
//        int id = 1;
//        Customer customer = Customer.builder()
//                .name("test")
//                .birthday("2001-11-03")
//                .age(20)
//                .country("ID").build();
//
//        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
//        assertEquals(customer, customerService.getCustomerByID(id));
//    }
//
//    @Test
//    void getCustomerByID_missingParameter_fail() {
//
//        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());
//        assertThrows(CustomerException.class, () -> customerService.getCustomerByID(1));
//    }
//
//    @Test
//    void addCustomer_validRequest_success() {
//        String name = "test";
//        String birthday = "2001-11-02";
//        String country = "ID";
//
//        Customer customer = Customer.builder()
//                .name(name)
//                .birthday(birthday)
//                .age(21)
//                .country(country).build();
//
//        customerService.addCustomer(name, birthday, country);
//
//        verify(customerRepository).save(customer);
//    }
//
//    @Test
//    void addCustomer_missingName_fail() {
//        String name = "";
//        String birthday = "2001-11-02";
//        String country = "ID";
//
//        assertThrows(CustomerException.class, () -> {
//            customerService.addCustomer(name, birthday, country);
//        } );
//    }
//
//    //TODO: add more test for birthday like future date, invalid birthday string, different date format, etc
////    @Test
////    public void addCustomer_missingBirthday_fail() {
////        String name = "John Doe";
////        String birthday = "";
////        String country = "US";
////
////        assertThrows(CustomerException.class, () -> {
////            customerService.addCustomer(name, birthday, country);
////        });
////    }
//
//    @Test
//    public void addCustomer_missingCountry_fail() {
//        String name = "John Doe";
//        String birthday = "1990-01-01";
//        String country = "";
//
//        assertThrows(CustomerException.class, () -> {
//            customerService.addCustomer(name, birthday, country);
//        });
//    }
//
//    @Test
//    public void addCustomer_invalidCountryLength_fail() {
//        String name = "John Doe";
//        String birthday = "1990-01-01";
//        String country = "USA";
//
//        assertThrows(CustomerException.class, () -> {
//            customerService.addCustomer(name, birthday, country);
//        });
//    }
//
//    @Test
//    void deleteCustomer() {
//    }
}