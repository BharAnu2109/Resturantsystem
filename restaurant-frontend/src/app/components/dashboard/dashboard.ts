import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RestaurantService } from '../../services/restaurant.service';
import { Order, Customer, MenuItem, OrderStatus } from '../../models/restaurant.models';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent implements OnInit {
  orders: Order[] = [];
  customers: Customer[] = [];
  menuItems: MenuItem[] = [];
  loading: boolean = false;

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.loading = true;
    
    Promise.all([
      this.restaurantService.getOrders().toPromise(),
      this.restaurantService.getCustomers().toPromise(),
      this.restaurantService.getMenuItems().toPromise()
    ]).then(([orders, customers, menuItems]) => {
      this.orders = orders || [];
      this.customers = customers || [];
      this.menuItems = menuItems || [];
      this.loading = false;
    }).catch(error => {
      console.error('Error loading dashboard data:', error);
      this.loading = false;
    });
  }

  getOrderStatusClass(status: OrderStatus): string {
    switch (status) {
      case OrderStatus.PENDING:
        return 'status-pending';
      case OrderStatus.CONFIRMED:
        return 'status-confirmed';
      case OrderStatus.PREPARING:
        return 'status-preparing';
      case OrderStatus.READY:
        return 'status-ready';
      case OrderStatus.DELIVERED:
        return 'status-delivered';
      case OrderStatus.CANCELLED:
        return 'status-cancelled';
      default:
        return '';
    }
  }

  updateOrderStatus(orderId: number, newStatus: OrderStatus): void {
    this.restaurantService.updateOrderStatus(orderId, newStatus).subscribe({
      next: (updatedOrder) => {
        const orderIndex = this.orders.findIndex(o => o.id === orderId);
        if (orderIndex !== -1) {
          this.orders[orderIndex] = updatedOrder;
        }
      },
      error: (error) => {
        console.error('Error updating order status:', error);
        alert('Error updating order status');
      }
    });
  }

  getCustomerName(customerId: number): string {
    const customer = this.customers.find(c => c.id === customerId);
    return customer ? customer.name : 'Unknown Customer';
  }

  getTotalRevenue(): number {
    return this.orders
      .filter(order => order.status === OrderStatus.DELIVERED)
      .reduce((total, order) => total + order.totalAmount, 0);
  }

  getPendingOrdersCount(): number {
    return this.orders.filter(order => 
      order.status === OrderStatus.PENDING || 
      order.status === OrderStatus.CONFIRMED || 
      order.status === OrderStatus.PREPARING
    ).length;
  }

  getAvailableMenuItemsCount(): number {
    return this.menuItems.filter(item => item.available).length;
  }
}
