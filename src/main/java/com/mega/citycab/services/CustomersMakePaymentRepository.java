package com.mega.citycab.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.citycab.models.CustomerMakePayment;
import com.mega.citycab.models.CustomerMakePaymentDto;

                                              // Model name  // Type of the primary key
public interface CustomersMakePaymentRepository extends JpaRepository<CustomerMakePayment, Integer> {

    public void save(CustomerMakePaymentDto customerMakePaymentDto);
    
}
