package com.restaurant.config;

import com.restaurant.model.MenuItem;
import com.restaurant.model.Customer;
import com.restaurant.repository.MenuItemRepository;
import com.restaurant.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize menu items
        if (menuItemRepository.count() == 0) {
            // Appetizers
            menuItemRepository.save(new MenuItem("Caesar Salad", "Fresh romaine lettuce with caesar dressing", new BigDecimal("8.99"), "Appetizers"));
            menuItemRepository.save(new MenuItem("Garlic Bread", "Toasted bread with garlic butter", new BigDecimal("5.99"), "Appetizers"));
            menuItemRepository.save(new MenuItem("Chicken Wings", "Spicy buffalo wings with ranch dip", new BigDecimal("12.99"), "Appetizers"));
            
            // Main Courses
            menuItemRepository.save(new MenuItem("Grilled Salmon", "Fresh salmon with herbs and lemon", new BigDecimal("18.99"), "Main Courses"));
            menuItemRepository.save(new MenuItem("Beef Burger", "Juicy beef patty with cheese and fries", new BigDecimal("14.99"), "Main Courses"));
            menuItemRepository.save(new MenuItem("Chicken Alfredo", "Creamy pasta with grilled chicken", new BigDecimal("16.99"), "Main Courses"));
            menuItemRepository.save(new MenuItem("Margherita Pizza", "Classic pizza with tomato, mozzarella, and basil", new BigDecimal("13.99"), "Main Courses"));
            
            // Desserts
            menuItemRepository.save(new MenuItem("Chocolate Cake", "Rich chocolate cake with vanilla ice cream", new BigDecimal("7.99"), "Desserts"));
            menuItemRepository.save(new MenuItem("Tiramisu", "Classic Italian dessert", new BigDecimal("6.99"), "Desserts"));
            
            // Beverages
            menuItemRepository.save(new MenuItem("Coffee", "Fresh brewed coffee", new BigDecimal("2.99"), "Beverages"));
            menuItemRepository.save(new MenuItem("Soft Drinks", "Coke, Pepsi, Sprite", new BigDecimal("2.49"), "Beverages"));
            menuItemRepository.save(new MenuItem("Fresh Juice", "Orange, Apple, or Cranberry", new BigDecimal("3.99"), "Beverages"));
        }
        
        // Initialize sample customers
        if (customerRepository.count() == 0) {
            customerRepository.save(new Customer("John Doe", "john.doe@email.com", "555-0101"));
            customerRepository.save(new Customer("Jane Smith", "jane.smith@email.com", "555-0102"));
            customerRepository.save(new Customer("Mike Johnson", "mike.johnson@email.com", "555-0103"));
        }
    }
}