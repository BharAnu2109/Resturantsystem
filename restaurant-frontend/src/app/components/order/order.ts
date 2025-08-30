import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RestaurantService } from '../../services/restaurant.service';
import { MenuItem, Customer, OrderRequest, OrderItemRequest } from '../../models/restaurant.models';

@Component({
  selector: 'app-order',
  imports: [CommonModule, FormsModule],
  templateUrl: './order.html',
  styleUrl: './order.css'
})
export class OrderComponent implements OnInit {
  menuItems: MenuItem[] = [];
  customers: Customer[] = [];
  cart: OrderItemRequest[] = [];
  selectedCustomerId: number = 0;
  orderNotes: string = '';
  isPlacingOrder: boolean = false;

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.loadMenuItems();
    this.loadCustomers();
  }

  loadMenuItems(): void {
    this.restaurantService.getAvailableMenuItems().subscribe({
      next: (items) => {
        this.menuItems = items;
      },
      error: (error) => {
        console.error('Error loading menu items:', error);
      }
    });
  }

  loadCustomers(): void {
    this.restaurantService.getCustomers().subscribe({
      next: (customers) => {
        this.customers = customers;
      },
      error: (error) => {
        console.error('Error loading customers:', error);
      }
    });
  }

  addToCart(menuItem: MenuItem): void {
    const existingItem = this.cart.find(item => item.menuItemId === menuItem.id);
    
    if (existingItem) {
      existingItem.quantity++;
    } else {
      this.cart.push({
        menuItemId: menuItem.id,
        quantity: 1,
        specialInstructions: ''
      });
    }
  }

  removeFromCart(menuItemId: number): void {
    this.cart = this.cart.filter(item => item.menuItemId !== menuItemId);
  }

  updateQuantity(menuItemId: number, quantity: number): void {
    const item = this.cart.find(item => item.menuItemId === menuItemId);
    if (item) {
      if (quantity <= 0) {
        this.removeFromCart(menuItemId);
      } else {
        item.quantity = quantity;
      }
    }
  }

  getMenuItem(id: number): MenuItem | undefined {
    return this.menuItems.find(item => item.id === id);
  }

  getCartTotal(): number {
    return this.cart.reduce((total, item) => {
      const menuItem = this.getMenuItem(item.menuItemId);
      return total + (menuItem ? menuItem.price * item.quantity : 0);
    }, 0);
  }

  placeOrder(): void {
    if (this.cart.length === 0 || this.selectedCustomerId === 0) {
      alert('Please select a customer and add items to cart before placing order.');
      return;
    }

    this.isPlacingOrder = true;

    const orderRequest: OrderRequest = {
      customerId: this.selectedCustomerId,
      notes: this.orderNotes,
      items: this.cart
    };

    this.restaurantService.createOrder(orderRequest).subscribe({
      next: (order) => {
        alert(`Order placed successfully! Order ID: ${order.id}`);
        this.cart = [];
        this.selectedCustomerId = 0;
        this.orderNotes = '';
        this.isPlacingOrder = false;
      },
      error: (error) => {
        console.error('Error placing order:', error);
        alert('Error placing order. Please try again.');
        this.isPlacingOrder = false;
      }
    });
  }
}
