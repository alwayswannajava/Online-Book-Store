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

### Category Management
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

**You can use Basic Auth**
![image](https://github.com/user-attachments/assets/2e9fc0d7-87c4-4c12-b6a2-b5bd3c443c94)

**Or Bearer token** 
![image](https://github.com/user-attachments/assets/440c6186-b7f0-49d4-9b63-c223fe4f6b5f)

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
### 📺 Visual 
## Authentication collection 

**Registration new user** 
![image](https://github.com/user-attachments/assets/994f7c5d-bb74-4f31-9a07-7e2cc8989062)

**Login user**
![image](https://github.com/user-attachments/assets/6fd5e00f-2270-49e5-922a-1132f1c1a042)

## Book collection
# Get all books
![image](https://github.com/user-attachments/assets/94e5afd8-1acd-4619-9d5b-37c983098202)

**Get book by ID**
![image](https://github.com/user-attachments/assets/2c353ae5-9a5f-4569-a5a8-8f2bee875ae3)

**Search book**
![image](https://github.com/user-attachments/assets/fd831514-93f8-4272-af2c-e926bf2a2d3e)

**Create new book** 
![image](https://github.com/user-attachments/assets/d6a2c15a-72d9-49b7-b7e5-0b5f2645add8)

**Update book by ID**
![image](https://github.com/user-attachments/assets/ad1f5479-a001-4e08-a048-7ed38398ddab)

**Delete book by ID**
![image](https://github.com/user-attachments/assets/0cce351b-6669-4dff-aa40-5bb21c83e6a3)

## Category collection
**Get all categories**
![image](https://github.com/user-attachments/assets/3b83d4a7-5519-41bc-af95-7f1203bf98b2)

**Get category by ID**
![image](https://github.com/user-attachments/assets/a5068cdf-a2d4-4b59-a956-3b3e271759f5)

**Get all books by category ID**
![image](https://github.com/user-attachments/assets/ca79111a-5c64-4dc9-9f67-b651a5b7634c)

**Create new category** 
![image](https://github.com/user-attachments/assets/25a4cd38-47d7-4337-9476-48a64b02fa26)

**Update category by ID**
![image](https://github.com/user-attachments/assets/26f1c6d2-ae82-4f85-a5b2-2b45e8129f9a)

**Delete category by ID**
![image](https://github.com/user-attachments/assets/8bbdaff8-f214-4aa4-afd7-3de8a9f3a1db)

## 👥 Team
- Mykhailo Kornukh - Backend Developer

## 📬 Contact
For any questions or feedback, please reach out to:
- Email: mykhailo.kornukh@gmail.com
- Telegram: @miSHYRIK
- Discord: stress_ful
