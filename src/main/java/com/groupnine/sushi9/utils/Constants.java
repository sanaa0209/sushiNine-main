package com.groupnine.sushi9.utils;

public class Constants {

    // Error messages
    public static final String NULL_OR_EMPTY_PARAMETER = "Parameter cannot be null or empty";
    public static final String INVALID_SALARY = "Salary must be greater than 0";
    public static final String INVALID_PRICE = "Price must be greater than 0";
    public static final String INVALID_QUANTITY = "Quantity must be greater than 0";
    public static final String INVALID_EMAIL = "Invalid email format";
    public static final String INVALID_PHONE = "Invalid phone number format";
    public static final String INVALID_DATE = "Invalid date";

    // Not found messages
    public static final String NO_EMPLOYEE_WITH_TAXCODE = "No employee found with taxCode: ";
    public static final String NO_CUSTOMER_WITH_TAXCODE = "No customer found with taxCode: ";
    public static final String NO_ORDER_WITH_ID = "No order found with id: ";
    public static final String NO_DISH_WITH_ID = "No dish found with id: ";
    public static final String NO_INGREDIENT_WITH_ID = "No ingredient found with id: ";
    public static final String NO_TABLE_WITH_ID = "No table found with id: ";

    // Already exists messages
    public static final String EMPLOYEE_ALREADY_EXISTS = "Employee already exists with taxCode: ";
    public static final String CUSTOMER_ALREADY_EXISTS = "Customer already exists with taxCode: ";
    public static final String DISH_ALREADY_EXISTS = "Dish already exists with name: ";

    // Business rules
    public static final String ORDER_ALREADY_COMPLETED = "Order is already completed";
    public static final String TABLE_OCCUPIED = "Table is already occupied";
    public static final String INSUFFICIENT_QUANTITY = "Insufficient quantity available";

    // Validation patterns
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String PHONE_PATTERN = "^[+]?[0-9]{10,15}$";
    public static final String TAXCODE_PATTERN = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$";
}