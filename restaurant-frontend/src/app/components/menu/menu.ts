import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RestaurantService } from '../../services/restaurant.service';
import { MenuItem } from '../../models/restaurant.models';

@Component({
  selector: 'app-menu',
  imports: [CommonModule],
  templateUrl: './menu.html',
  styleUrl: './menu.css'
})
export class MenuComponent implements OnInit {
  menuItems: MenuItem[] = [];
  categories: string[] = [];
  selectedCategory: string = 'All';
  filteredItems: MenuItem[] = [];

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.loadMenuItems();
  }

  loadMenuItems(): void {
    this.restaurantService.getAvailableMenuItems().subscribe({
      next: (items) => {
        this.menuItems = items;
        this.extractCategories();
        this.filterItems();
      },
      error: (error) => {
        console.error('Error loading menu items:', error);
      }
    });
  }

  extractCategories(): void {
    const categorySet = new Set(this.menuItems.map(item => item.category));
    this.categories = ['All', ...Array.from(categorySet)];
  }

  filterItems(): void {
    if (this.selectedCategory === 'All') {
      this.filteredItems = this.menuItems;
    } else {
      this.filteredItems = this.menuItems.filter(item => item.category === this.selectedCategory);
    }
  }

  onCategoryChange(category: string): void {
    this.selectedCategory = category;
    this.filterItems();
  }
}
