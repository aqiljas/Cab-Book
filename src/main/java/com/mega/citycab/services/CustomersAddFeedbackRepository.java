package com.mega.citycab.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.citycab.models.CustomerAddFeedback;
import com.mega.citycab.models.CustomerAddFeedbackDto;

                                              // Model name  // Type of the primary key
public interface CustomersAddFeedbackRepository extends JpaRepository<CustomerAddFeedback, Integer> {

    public void save(CustomerAddFeedbackDto customerAddFeedbackDto);
    
}