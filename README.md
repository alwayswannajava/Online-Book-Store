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
  - MySQL 8.0.37
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

The API will be available at `http://localhost:8081`

### API Documentation
After starting the application, you can access the Swagger UI at:
`http://localhost:8081/swagger-ui.html`

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
POST /api/auth/register - Register new user
![image](https://github.com/user-attachments/assets/3a28eb03-cf0e-4a29-a547-daf44f914b3a)

POST /api/auth/login - Login user
![image](https://github.com/user-attachments/assets/3e2eb75c-f90b-495a-8a53-841ec4c2731c)
```

### Book Controller
```
GET    /api/books - Get all books
POST   /api/books - Create new book (Admin only)
GET    /api/books/{id} - Get book by ID
GET    /api/books/search - Search book by params
PUT    /api/books/{id} - Update book (Admin only)
DELETE /api/books/{id} - Delete book (Admin only)
```

### Shopping Cart Controller
```
GET    /api/cart - Get user's shopping cart
POST   /api/cart - Add item to cart
DELETE /api/cart/{itemId} - Remove item from cart
PUT    /api/cart/{itemId} - Update cart item quantity
```

### Order Controller
```
POST   /api/orders - Create order from cart
GET    /api/orders - Get user's orders
GET    /api/orders/{id} - Get order details
PATCH    /api/orders/{id}/status - Update order status (Admin only)
```

## 👥 Team
- Mykhailo Kornukh - Backend Developer

## 📬 Contact
For any questions or feedback, please reach out to:
- Email: mykhailo.kornukh@gmail.com
