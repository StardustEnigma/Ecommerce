# 🛍️ E-Commerce Platform

A production-ready Spring Boot REST API for a comprehensive e-commerce application with user management, product catalog, shopping cart, and order processing capabilities.

## ✨ Features

- 👤 **User Management** - Create, update, and delete user accounts with proper validation
- 📦 **Product Management** - Full CRUD operations for products organized by categories
- 🏷️ **Category Management** - Organize and manage product categories
- 🛒 **Shopping Cart** - Add/remove items, update quantities, and manage cart operations
- 📋 **Order Management** - Place orders, view history, and cancel orders with ownership validation
- 🔐 **Role-Based Access Control** - Separate public and admin endpoints
- ⚠️ **Custom Exception Handling** - Comprehensive error messages and proper HTTP status codes
- 📚 **API Documentation** - Swagger/OpenAPI integration for interactive API docs
- ✅ **Input Validation** - Request body validation with detailed error messages

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Framework** | Spring Boot 3.x |
| **Language** | Java 17+ |
| **Database** | JPA/Hibernate (SQL) |
| **Build Tool** | Maven |
| **API Design** | RESTful API with OpenAPI 3.0 |
| **Validation** | Spring Validation (Jakarta) |

## 📁 Project Structure

```
src/main/java/com/ecommerce/
├── controller/              # REST endpoints
│   ├── UserController.java
│   ├── ProductController.java
│   ├── OrderController.java
│   ├── CategoryController.java
│   └── CartController.java
├── service/                 # Business logic
│   ├── UserService.java & UserServiceImpl.java
│   ├── ProductService.java & ProductServiceImpl.java
│   ├── OrderService.java & OrderServiceImpl.java
│   ├── CategoryService.java & CategoryServiceImpl.java
│   └── CartService.java & CartServiceImpl.java
├── model/                   # Entity models
│   ├── User.java
│   ├── Product.java
│   ├── Category.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Order.java
│   └── OrderItem.java
├── repositories/            # JPA repositories
├── dto/                     # Data Transfer Objects
├── exception/               # Custom exceptions
├── config/                  # Configuration classes
└── EcommerceApplication.java
```

## 🚀 Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL/PostgreSQL database

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/ecommerce.git
   cd Ecommerce
   ```

2. **Configure database**
   ```properties
   # application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - API Docs: `http://localhost:8080/v3/api-docs`

## 📡 API Endpoints

### 👤 User Management
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/admin/users` | Get all users | Admin |
| POST | `/api/public/users` | Create new user | Public |
| PUT | `/api/public/users/{userId}` | Update user | Public |
| DELETE | `/api/public/users/{userId}` | Delete user | Public |

**Example:**
```bash
curl -X POST http://localhost:8080/api/public/users \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "john_doe",
    "userEmail": "john@example.com",
    "userPassword": "secure_password"
  }'
```

### 📦 Product Management
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/public/products` | Get all products | Public |
| POST | `/api/admin/{categoryId}/products` | Create product | Admin |
| PUT | `/api/admin/products/{productId}` | Update product | Admin |
| DELETE | `/api/admin/products/{productId}` | Delete product | Admin |

### 🏷️ Category Management
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/public/categories` | Get all categories | Public |
| POST | `/api/public/categories` | Create category | Public |
| PUT | `/api/admin/categories/{categoryId}` | Update category | Admin |
| DELETE | `/api/admin/categories/{categoryId}` | Delete category | Admin |

### 🛒 Shopping Cart
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/public/users/{userId}/cart` | View cart |
| POST | `/api/public/users/{userId}/cart/items` | Add product |
| PUT | `/api/public/users/{userId}/cart/items` | Update quantity |
| DELETE | `/api/public/users/{userId}/cart/items/{productId}` | Remove product |
| PUT | `/api/public/users/{userId}/cart/clear` | Clear cart |

**Example:**
```bash
curl -X POST http://localhost:8080/api/public/users/1/cart/items \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 5,
    "quantity": 2
  }'
```

### 📋 Order Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/public/users/{userId}/orders` | Place order |
| GET | `/api/public/users/{userId}/orders/history` | View order history |
| DELETE | `/api/public/users/{userId}/orders/cancel/{orderId}` | Cancel order |

## 🔧 Key Features Explained

### Order Processing
- **Cart to Order Conversion**: Automatically converts cart items to order items
- **Total Calculation**: Accurate total amount calculation based on product prices and quantities
- **Cart Clearance**: Cart is automatically cleared after successful order placement
- **Ownership Validation**: Users can only cancel their own orders
- **Order Status Tracking**: Orders are marked as CREATED or CANCELLED

### Shopping Cart Management
- **Auto Cart Creation**: Cart is automatically created for new users
- **Quantity Management**: Add items individually or update quantities in bulk
- **Price Caching**: Product prices are stored at purchase time for historical accuracy
- **Real-time Calculations**: Cart totals are calculated on-the-fly

### Exception Handling
The application implements custom exceptions for better error management:
- `UserNotFoundException` - User doesn't exist
- `ProductNotFoundException` - Product doesn't exist
- `CartNotFoundException` - User's cart doesn't exist
- `OrderNotFoundException` - Order doesn't exist
- `CategoryNotFoundException` - Category doesn't exist
- `EmptyCartException` - Cannot place order with empty cart
- `OrderOwnershipException` - User doesn't own the order
- `CartItemNotFoundException` - Item not in cart

## 🔒 Security & Authorization

Currently implements endpoint-level role separation:
- **Public Endpoints** (`/api/public/`) - Accessible to all users
- **Admin Endpoints** (`/api/admin/`) - Restricted to admin users

### Future Enhancement: JWT Authentication
```java
// Coming soon
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // JWT filter configuration
}
```

## 📊 Database Schema Highlights

- **Cascade Operations**: Properly configured cascade delete for orders and cart items
- **Indexes**: Optimized queries with proper indexing
- **Relationships**: Well-designed OneToMany and ManyToOne relationships
- **Data Integrity**: Foreign key constraints ensure data consistency

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
- Unit tests for service layer
- Integration tests for controller layer
- Exception handling validation

## 🚀 Performance Optimizations

- Transaction management for data consistency
- Lazy loading for related entities
- Efficient query methods in repositories
- Stream API usage for calculations

## 📈 Future Enhancements

- [ ] JWT authentication and authorization
- [ ] Payment gateway integration (Stripe/PayPal)
- [ ] Product reviews and ratings system
- [ ] Inventory management and stock tracking
- [ ] Email notifications for orders
- [ ] Advanced search and filtering
- [ ] Product wishlist/favorites
- [ ] Admin dashboard
- [ ] User profile management
- [ ] Order tracking with status updates

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Standards
- Follow Google Java Style Guide
- Write meaningful commit messages
- Add JavaDoc for public methods
- Ensure all tests pass before submitting PR

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💼 Contact & Support

- **Author**: Atharva
- **Issues**: Create an issue on GitHub
- **Discussions**: Use GitHub Discussions for questions

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [OpenAPI 3.0 Specification](https://spec.openapis.org/oas/v3.0.3)
- [RESTful API Design Best Practices](https://restfulapi.net/)

---

**Last Updated**: 2025 | **Version**: 1.0.0
