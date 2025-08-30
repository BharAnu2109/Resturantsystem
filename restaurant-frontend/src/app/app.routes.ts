import { Routes } from '@angular/router';
import { MenuComponent } from './components/menu/menu';
import { OrderComponent } from './components/order/order';
import { DashboardComponent } from './components/dashboard/dashboard';

export const routes: Routes = [
  { path: '', redirectTo: '/menu', pathMatch: 'full' },
  { path: 'menu', component: MenuComponent },
  { path: 'order', component: OrderComponent },
  { path: 'dashboard', component: DashboardComponent }
];
