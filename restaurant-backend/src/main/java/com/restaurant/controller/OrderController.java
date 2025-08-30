package com.restaurant.controller;

import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.model.Customer;
import com.restaurant.model.MenuItem;
import com.restaurant.repository.OrderRepository;
import com.restaurant.repository.CustomerRepository;
import com.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findByOrderByOrderTimeDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            List<Order> orders = orderRepository.findByCustomer(customer.get());
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Optional<Customer> customer = customerRepository.findById(orderRequest.getCustomerId());
        
        if (customer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Order order = new Order(customer.get());
        order.setNotes(orderRequest.getNotes());
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Optional<MenuItem> menuItem = menuItemRepository.findById(itemRequest.getMenuItemId());
            if (menuItem.isPresent()) {
                OrderItem orderItem = new OrderItem(order, menuItem.get(), itemRequest.getQuantity());
                orderItem.setSpecialInstructions(itemRequest.getSpecialInstructions());
                order.getOrderItems().add(orderItem);
                totalAmount = totalAmount.add(orderItem.getPrice());
            }
        }
        
        order.setTotalAmount(totalAmount);
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody Order.OrderStatus status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return ResponseEntity.ok(orderRepository.save(order));
        }
        
        return ResponseEntity.notFound().build();
    }

    // Inner classes for request DTOs
    public static class OrderRequest {
        private Long customerId;
        private String notes;
        private List<OrderItemRequest> items;
        
        // Getters and setters
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
        public List<OrderItemRequest> getItems() { return items; }
        public void setItems(List<OrderItemRequest> items) { this.items = items; }
    }
    
    public static class OrderItemRequest {
        private Long menuItemId;
        private Integer quantity;
        private String specialInstructions;
        
        // Getters and setters
        public Long getMenuItemId() { return menuItemId; }
        public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getSpecialInstructions() { return specialInstructions; }
        public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }
    }
}