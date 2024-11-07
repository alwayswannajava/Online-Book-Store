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

Or with AWS http://54.209.184.153/api/swagger-ui/index.html#/

## 🎯 Development Challenges & Solutions

### Challenge 1: Performance with Large Dataset
When dealing with large book catalogs, we encountered performance issues with pagination and filtering. 

**Solution**: Implemented database indexing and query optimization using Spring Data JPA's and Specification API, resulting in a 70% improvement in response times.

**Example**: Let's see how it works with not very large dataset, but it still appropriate for perfomance of using Spring Data JPA and Specification API. For example, we have created 200 records of books. Mainly, perfomance gets increase by pageable, because not all data
will show with pageable parameter, it's very simple. Moreover, Specification API allows us to build dynamical queries for books. As result, we can see perfomance get increased by 70%.

| Records | Pageable + Specification | Time ms |
| :---            |     :---:   |          ---:|
| 200      | Off  | 419         |
| 200      | On   | 294         |

### Request without pageable + specification API
![image](https://github.com/user-attachments/assets/25841ee8-6d47-4370-a91e-3e5e896a72aa)

### Request with pageable + specification API
![image](https://github.com/user-attachments/assets/0e342340-bbff-42ab-bd37-eed2b8efb2e0)

### Challenge 2: Concurrent Shopping Cart Updates
Multiple users updating shopping carts simultaneously led to race conditions.

**Solution**: Implemented database transaction management to ensure data consistency.

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
GET    /api/orders/{id}/items - Get order items by order ID
GET    /api/orders/{orderId}/items/{itemId} - Get order item by order item ID and order ID
POST   /api/orders - Create order from cart
PATCH  /api/orders/{id} - Update order status (Admin only)
```
## 📺 Visual 
### Flowchart 
![image](https://github.com/user-attachments/assets/c21bce53-0ad7-414a-b45a-434955b4ad63)

### Authentication collection 
```
https://web.postman.co/workspace/My-Workspace~094913b8-3731-4cce-8108-9a9ee90872b2/api/8847db5f-52c1-4c57-859b-79c52f435a47/collection/25455394-423d22c9-14bb-42ee-a469-4bf86dde8005?action=share&source=copy-link&creator=25455394
```

### Book collection
```
https://web.postman.co/workspace/My-Workspace~094913b8-3731-4cce-8108-9a9ee90872b2/api/8847db5f-52c1-4c57-859b-79c52f435a47/collection/25455394-3da2884b-74c6-48ec-aee2-983c1293d0a3?action=share&source=copy-link&creator=25455394
```

### Category collection
```
https://web.postman.co/workspace/My-Workspace~094913b8-3731-4cce-8108-9a9ee90872b2/api/8847db5f-52c1-4c57-859b-79c52f435a47/collection/25455394-48358c00-d56f-4199-969c-18508b76f75a?action=share&source=copy-link&creator=25455394
```

### Shopping cart collection
```
https://web.postman.co/workspace/My-Workspace~094913b8-3731-4cce-8108-9a9ee90872b2/api/8847db5f-52c1-4c57-859b-79c52f435a47/collection/25455394-f3785056-f941-40a4-bf6e-cc91c38c27a7?action=share&source=copy-link&creator=25455394
```

### Order collection
```
https://web.postman.co/workspace/My-Workspace~094913b8-3731-4cce-8108-9a9ee90872b2/api/8847db5f-52c1-4c57-859b-79c52f435a47/collection/25455394-d9280bc4-b4b7-440a-8d02-6fb24eaf9fe5?action=share&source=copy-link&creator=25455394
```

## 👥 Team
- Mykhailo Kornukh - Junior Backend Developer

## 📬 Contact
For any questions or feedback, please reach out to:
- Email: mykhailo.kornukh@gmail.com
- Telegram: @miSHYRIK
- Discord: stress_ful
