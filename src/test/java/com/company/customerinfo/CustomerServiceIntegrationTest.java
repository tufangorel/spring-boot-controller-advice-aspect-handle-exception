package com.company.customerinfo;


import com.company.customerinfo.model.Address;
import com.company.customerinfo.model.Customer;
import com.company.customerinfo.service.CustomerService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = CustomerInfoApplication.class)
@ActiveProfiles("test")
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Order(1)
    @Test
    public void saveCustomerTest() {

        Customer customer = new Customer();
        customer.setName("name1");
        customer.setAge(1);

        Address address = new Address();
        address.setCountry("TR");
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);
        customer.setAddresses(addresses);

        Customer savedRecord = customerService.save(customer);
        assertThat( savedRecord.getAddresses() != null);

    }

    @Order(2)
    @Test
    public void findAllTest() {
        List<Customer> customerList =customerService.findAll();
        assertThat( customerList.size() > 0);
    }

}
