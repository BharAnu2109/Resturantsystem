package com.restaurant.repository;

import com.restaurant.model.Order;
import com.restaurant.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
    List<Order> findByStatus(Order.OrderStatus status);
    
    @Query("SELECT o FROM Order o ORDER BY o.orderTime DESC")
    List<Order> findAllOrdersByOrderTimeDesc();
}