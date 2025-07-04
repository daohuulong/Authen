# Spring Boot Authentication with OAuth2.0

This project demonstrates a complete Spring Boot application with OAuth2.0 authentication using Google and Facebook SSO, following the MVC pattern.

## Features

- Spring Security with OAuth2.0 authentication
- Google and Facebook SSO integration
- MySQL database integration
- RESTful API endpoints
- Thymeleaf web interface
- Docker containerization
- Sample data initialization

## Project Structure

```
src/
├── main/
│   ├── java/org/example/authen/
│   │   ├── config/          # Security configuration
│   │   ├── controller/      # REST controllers
│   │   ├── model/          # Entity models
│   │   ├── repository/     # Data access layer
│   │   └── service/        # Business logic
│   └── resources/
│       ├── templates/      # Thymeleaf templates
│       └── application*.properties
```

## Quick Start

### Using Docker Compose (Recommended)

1. Build and run the application:
```bash
docker-compose up --build
```

2. Access the application:
- Web Interface: http://localhost:8080
- API Endpoints: http://localhost:8080/api/*

### Local Development

1. Start MySQL server
2. Update `application.properties` with your database credentials
3. Run the application:
```bash
mvn spring-boot:run
```

## API Endpoints

### Authentication
- `GET /` - Home page
- `GET /login` - Login page
- `GET /api/auth/user` - Get current user
- `POST /api/auth/logout` - Logout

### User Management
- `GET /api/users` - List all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

## OAuth2 Configuration

Update the following in `application.properties`:

```properties
# Google OAuth2
spring.security.oauth2.client.registration.google.client-id=your-google-client-id
spring.security.oauth2.client.registration.google.client-secret=your-google-client-secret

# Facebook OAuth2
spring.security.oauth2.client.registration.facebook.client-id=your-facebook-app-id
spring.security.oauth2.client.registration.facebook.client-secret=your-facebook-app-secret
```

## Database

The application uses MySQL with the following sample data:
- 10 sample users with different authentication providers
- Auto-generated user IDs
- Timestamps for created/updated dates

## Docker Services

- **MySQL**: Database server on port 3306
- **App**: Spring Boot application on port 8080

## Building for Production

```bash
# Build JAR
mvn clean package

# Build Docker image
docker build -t authen-app .

# Run with Docker Compose
docker-compose up -d
```

## Testing

Access the application at http://localhost:8080 and test:
1. Login with Google/Facebook
2. View user profile
3. Test API endpoints
4. Check database entries