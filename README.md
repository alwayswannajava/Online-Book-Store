# 📚 Online Bookstore API

## Introduction
Welcome to the Online Bookstore API! This project was born from the desire to create a modern, efficient solution for managing online book sales. In today's digital age, bookstores need robust systems that can handle complex operations while maintaining simplicity for users. Our API provides a comprehensive solution for managing books, user accounts, shopping carts, and orders with a focus on security and scalability.

## 🛠 Technologies & Tools
- **Backend Framework**: Spring Boot 3.2.0
- **Security**: 
  - Spring Security 
  - JWT Authentication
  - BCrypt password encoding
- **Database**: 
  - MySQL
  - Spring Data JPA
  - Liquibase for database migrations
- **Documentation**: Swagger/OpenAPI 3.0
- **Containerization**: Docker
- **Cloud Services**: AWS (EC2, RDS)
- **Testing**: 
  - JUnit 5
  - Mockito
  - Testcontainers
- **Other Tools**:
  - Maven
  - Lombok
  - MapStruct
  - Spring Validation

## 🚀 Features
### User Management
- User registration and authentication
- JWT-based authorization
- Role-based access control (USER, ADMIN)

### Book Management
- CRUD operations for books
- Search and filter functionality
- Categories and authors management

### Book Management
- CRUD operations for categories
- Categories management

### Shopping Features
- Shopping cart management
- Order processing

### Admin Features
- User management
- Order management
- Book inventory control

## 🏃‍♂️ Getting Started

### Prerequisites
- JDK 17 or higher
- Docker and Docker Compose
- MySQL 15
- Maven 3.8+

### Installation Steps

1. Clone the repository
```bash
git clone https://github.com/alwayswannajava/Online-Book-Store.git
cd Online-Book-Store
```

2. Configure environment variables
```bash
cp .env.example .env
# Edit .env file with your configurations
```

3. Build the project
```bash
mvn clean install
```

4. Run with Docker Compose
```bash
docker-compose up -d
```

The API will be available at `http://localhost:8081/api`

### API Documentation 
After starting the application, you can access the Swagger UI at:
`http://localhost:8081/api/swagger-ui.html`
#### Credentials for swagger: 
Login: user@example.com
Password: 1234

## 🎯 Development Challenges & Solutions

### Challenge 1: Performance with Large Dataset
When dealing with large book catalogs, we encountered performance issues with pagination and filtering. 

**Solution**: Implemented database indexing and query optimization using Spring Data JPA's Specification API, resulting in a 70% improvement in response times.

### Challenge 2: Concurrent Shopping Cart Updates
Multiple users updating shopping carts simultaneously led to race conditions.

**Solution**: Implemented optimistic locking with version control and database transaction management to ensure data consistency.

### Challenge 3: Security Implementation
Implementing a robust security system while maintaining ease of use was challenging.

**Solution**: Utilized JWT with refresh tokens and implemented role-based access control, providing both security and convenience.

## 📝 API Endpoints

### Authentication Controller
```
POST /api/auth/registration - Register new user
POST /api/auth/login - Login user
```

### Book Controller
```
GET    /api/books - Get all books
GET    /api/books/{id} - Get book by ID
GET    /api/books/search - Search book by params
POST   /api/books - Create new book (Admin only)
PUT    /api/books/{id} - Update book (Admin only)
DELETE /api/books/{id} - Delete book (Admin only)
```

### Category Controller
```
GET    /api/categories - Get all categories
GET    /api/categories/{id} - Get category by ID
GET    /api/categories/{id}/books - Get all books by category ID
POST   /api/categories - Create new category (Admin only)
PUT    /api/categories/{id} - Update category (Admin only)
DELETE /api/categories/{id} - Delete category (Admin only)
```

### Shopping Cart Controller
```
GET    /api/cart - Get user's shopping cart
POST   /api/cart - Add item to cart
PUT    /api/cart/items/{id} - Update cart item quantity
DELETE /api/cart/items/{id} - Remove item from cart
```

### Order Controller
```
GET    /api/orders - Get user's orders
GET    /api/orders/{id}/items - Get order items by order id
GET    /api/orders/{orderId}/items/{itemId} - Get order item by order item id and order id
POST   /api/orders - Create order from cart
PATCH    /api/orders/{id} - Update order status (Admin only)
```

## 👥 Team
- Mykhailo Kornukh - Backend Developer

## 📬 Contact
For any questions or feedback, please reach out to:
- Email: mykhailo.kornukh@gmail.com
- Telegram: @miSHYRIK
- Discord: stress_ful
