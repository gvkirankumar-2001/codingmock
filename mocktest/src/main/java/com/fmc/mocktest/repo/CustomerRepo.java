package com.fmc.mocktest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmc.mocktest.entities.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
