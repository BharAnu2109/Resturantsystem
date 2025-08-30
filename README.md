# Restaurant Management System

A complete full-stack restaurant management system built with Spring Boot (backend) and Angular (frontend).

## Features

### 🍽️ Menu Management
- Browse menu items by category (Appetizers, Main Courses, Desserts, Beverages)
- View item details including name, description, price, and availability
- Responsive grid layout for optimal viewing

### 📋 Order Management
- Add items to cart with quantity controls
- Select customers from registered customer list
- Place orders with optional notes
- Real-time cart total calculation

### 📊 Dashboard
- Key performance metrics:
  - Total revenue from delivered orders
  - Pending orders count
  - Available menu items count
  - Total registered customers
- Recent orders overview with status tracking
- Customer list with contact information
- Order status management

## Technology Stack

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.1.5
- **Database**: H2 (in-memory)
- **ORM**: JPA/Hibernate
- **API**: REST with JSON
- **Build Tool**: Maven

### Frontend (Angular)
- **Framework**: Angular (latest)
- **Styling**: CSS with responsive design
- **HTTP Client**: Angular HttpClient
- **Routing**: Angular Router

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Maven 3.6+

### Running the Application

1. **Start the Backend**:
   ```bash
   cd restaurant-backend
   mvn spring-boot:run
   ```
   Backend will run on http://localhost:8080

2. **Start the Frontend**:
   ```bash
   cd restaurant-frontend
   npm install
   ng serve
   ```
   Frontend will run on http://localhost:4200

### Sample Data
The application automatically loads sample data including:
- 12 menu items across 4 categories
- 3 sample customers (John Doe, Jane Smith, Mike Johnson)

## API Endpoints

### Menu
- `GET /api/menu` - Get all menu items
- `GET /api/menu/available` - Get available menu items
- `GET /api/menu/category/{category}` - Get items by category

### Customers
- `GET /api/customers` - Get all customers
- `POST /api/customers` - Create new customer

### Orders
- `GET /api/orders` - Get all orders
- `POST /api/orders` - Place new order
- `PUT /api/orders/{id}/status` - Update order status

## Screenshots

### Menu Page
![Menu Page](https://github.com/user-attachments/assets/a38f1249-2b17-465c-b335-5bed802ca29b)

### Order Page
![Order Page](https://github.com/user-attachments/assets/7a1f4f27-5da5-45db-a975-dd151c025854)

### Dashboard
![Dashboard](https://github.com/user-attachments/assets/256614c8-ef3e-4849-9b2d-c0406601beb5)

## Project Structure

```
Resturantsystem/
├── restaurant-backend/          # Spring Boot backend
│   ├── src/main/java/com/restaurant/
│   │   ├── model/              # JPA entities
│   │   ├── repository/         # Data repositories
│   │   ├── controller/         # REST controllers
│   │   └── config/             # Configuration classes
│   └── src/main/resources/
│       └── application.properties
├── restaurant-frontend/         # Angular frontend
│   ├── src/app/
│   │   ├── components/         # Angular components
│   │   ├── services/           # HTTP services
│   │   └── models/             # TypeScript interfaces
│   └── src/assets/
└── README.md
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source and available under the [MIT License](LICENSE).