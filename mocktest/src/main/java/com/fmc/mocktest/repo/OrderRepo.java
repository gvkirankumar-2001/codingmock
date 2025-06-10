package com.fmc.mocktest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fmc.mocktest.entities.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
	List<Order> getAllOrdersByCustomerId(@Param("customerId") Long customerId);

	@Query("SELECT SUM(o.totalAmount) from Order o WHERE o.customer.id = :customerId")
	Double getTotalAmountByCustomerId(@Param("customerId") Long customerId);
}
