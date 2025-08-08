# GitHub Copilot Instructions

This document provides guidelines for working with GitHub Copilot to maintain high code quality and readability.

## Core Principles

### 1. Self-Documenting Code Over Comments
- **DO**: Use descriptive variable, method, and class names that explain their purpose
- **AVOID**: Writing comments to explain what the code does

```java
// ❌ Avoid
int d; // days
List<User> users = getUsers();
// Calculate total price including tax
double total = price * 1.08;

// ✅ Prefer
int daysSinceLastLogin;
List<User> activeUsersInCurrentSession = getUsersCurrentlyLoggedIn();
double totalPriceIncludingTax = calculatePriceWithTax(basePrice, TAX_RATE);
```

### 2. Naming Conventions

#### Variables
- Use descriptive nouns that clearly indicate what the variable contains
- Boolean variables should start with `is`, `has`, `can`, `should`
- Collections should indicate they contain multiple items

```java
// ✅ Good examples
boolean isUserAuthenticated;
boolean hasValidSubscription;
List<Customer> customersWithOverduePayments;
Map<String, OrderStatus> orderStatusByCustomerId;
```

#### Methods
- Use verbs that clearly describe what the method does
- Include the return type concept in the name when helpful
- Be specific about the action being performed

```java
// ✅ Good examples
public List<Order> findOrdersByCustomerIdAndDateRange(String customerId, LocalDate startDate, LocalDate endDate)
public boolean validateEmailFormat(String emailAddress)
public void sendPaymentReminderToCustomer(Customer customer)
public Optional<User> findUserByEmailAddress(String emailAddress)
```

#### Classes
- Use nouns that clearly describe the entity or responsibility
- Follow single responsibility principle in naming

```java
// ✅ Good examples
public class CustomerPaymentProcessor
public class EmailNotificationService
public class OrderValidationResult
public class DatabaseConnectionManager
```

### 3. When Comments Are Acceptable

Comments should only be used for:
- **Why** something is done, not **what** is being done
- Business logic explanations that aren't obvious from the code
- API documentation (JavaDoc)
- TODO items for future improvements

```java
// ✅ Acceptable comment - explains WHY
// Using exponential backoff to handle rate limiting from external API
Thread.sleep(calculateBackoffDelay(attemptNumber));

// ✅ Acceptable comment - business context
// Customer gets free shipping if order total exceeds company promotion threshold
if (orderTotal.isGreaterThan(FREE_SHIPPING_THRESHOLD)) {
    applyFreeShippingDiscount();
}
```

### 4. Code Structure Guidelines

#### Extract Methods for Clarity
Instead of comments explaining code blocks, extract them into well-named methods:

```java
// ❌ Avoid
public void processOrder(Order order) {
    // Validate order items
    for (OrderItem item : order.getItems()) {
        if (item.getQuantity() <= 0 || item.getPrice().isNegative()) {
            throw new InvalidOrderException("Invalid item: " + item.getId());
        }
    }
    
    // Calculate totals
    BigDecimal subtotal = BigDecimal.ZERO;
    for (OrderItem item : order.getItems()) {
        subtotal = subtotal.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
    }
    
    // Apply discounts
    BigDecimal discount = calculateCustomerDiscount(order.getCustomer());
    BigDecimal total = subtotal.subtract(discount);
    
    order.setTotal(total);
}

// ✅ Prefer
public void processOrder(Order order) {
    validateAllOrderItems(order.getItems());
    BigDecimal subtotal = calculateOrderSubtotal(order.getItems());
    BigDecimal finalTotal = applyCustomerDiscounts(subtotal, order.getCustomer());
    order.setTotal(finalTotal);
}
```