-- SQL Queries for Sales Data Analysis

-- 1. Calculate total sales volume for March 2024
SELECT SUM(amount) as march_sales
FROM orders
WHERE strftime('%Y-%m', order_date) = '2024-03';

-- Expected result: 27,000
-- This query:
-- - Uses SUM() to total the amount column
-- - Filters for March 2024 using strftime
-- - Returns only orders from March 2024

-- 2. Find the customer who spent the most overall
SELECT 
    customer,
    SUM(amount) as total_spent
FROM orders
GROUP BY customer
ORDER BY total_spent DESC
LIMIT 1;

-- Expected result: Alice with 20,000
-- This query:
-- - Groups orders by customer
-- - Calculates total spent per customer
-- - Orders by total spent in descending order
-- - Limits to top spender

-- 3. Calculate the average order value
SELECT 
    AVG(amount) as average_order_value
FROM orders;

-- Expected result: 6,000
-- This query:
-- - Uses AVG() to calculate mean order value
-- - Includes all orders in the calculation
-- - Returns a single value representing average order

-- Additional Analysis: Monthly breakdown of sales
SELECT 
    strftime('%Y-%m', order_date) as month,
    SUM(amount) as monthly_sales,
    COUNT(*) as number_of_orders,
    AVG(amount) as avg_order_value
FROM orders
GROUP BY strftime('%Y-%m', order_date)
ORDER BY month; 