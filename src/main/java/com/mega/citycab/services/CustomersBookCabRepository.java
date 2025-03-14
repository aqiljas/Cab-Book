package com.mega.citycab.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.citycab.models.CustomerBookCab;
import com.mega.citycab.models.CustomerBookCabDto;

                                              // Model name  // Type of the primary key
public interface CustomersBookCabRepository extends JpaRepository<CustomerBookCab, Integer> {

    public void save(CustomerBookCabDto customerBookCabDto);
    
}
    