
SELECT SUM(amount) as march_sales
FROM orders
WHERE strftime('%Y-%m', order_date) = '2024-03';


SELECT 
    customer,
    SUM(amount) as total_spent
FROM orders
GROUP BY customer
ORDER BY total_spent DESC
LIMIT 1;

SELECT 
    AVG(amount) as average_order_value
FROM orders;

SELECT 
    strftime('%Y-%m', order_date) as month,
    SUM(amount) as monthly_sales,
    COUNT(*) as number_of_orders,
    AVG(amount) as avg_order_value
FROM orders
GROUP BY strftime('%Y-%m', order_date)
ORDER BY month; 