// Store expenses in an array
let expenses = [];

// Function to add a new expense
function addExpense() {
    const category = document.getElementById('category').value;
    const amount = parseFloat(document.getElementById('amount').value);

    if (category && amount > 0) {
        expenses.push({ category, amount });
        updateExpenseTable();
        clearInputs();
    } else {
        alert('Please enter valid category and amount!');
    }
}

// Function to clear input fields
function clearInputs() {
    document.getElementById('category').value = '';
    document.getElementById('amount').value = '';
}

// Function to update the expense table
function updateExpenseTable() {
    const expenseList = document.getElementById('expenseList');
    expenseList.innerHTML = '';

    expenses.forEach((expense, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${expense.category}</td>
            <td>$${expense.amount.toLocaleString()}</td>
            <td>
                <button class="delete-btn" onclick="deleteExpense(${index})">Delete</button>
            </td>
        `;
        expenseList.appendChild(row);
    });
}

// Function to delete an expense
function deleteExpense(index) {
    expenses.splice(index, 1);
    updateExpenseTable();
}

// Function to calculate and display expense statistics
function calculateExpenses() {
    if (expenses.length === 0) {
        alert('Please add some expenses first!');
        return;
    }

    // Calculate total expenses
    const total = expenses.reduce((sum, expense) => sum + expense.amount, 0);
    
    // Calculate average daily expense (assuming 30 days per month)
    const averageDaily = total / 30;
    
    // Get top 3 expenses
    const top3 = [...expenses]
        .sort((a, b) => b.amount - a.amount)
        .slice(0, 3);

    // Update the display
    document.getElementById('totalExpenses').textContent = `$${total.toLocaleString()}`;
    document.getElementById('averageExpense').textContent = `$${averageDaily.toLocaleString(undefined, {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    })}`;

    const topExpensesList = document.getElementById('topExpenses');
    topExpensesList.innerHTML = '';
    top3.forEach(expense => {
        const li = document.createElement('li');
        li.textContent = `${expense.category}: $${expense.amount.toLocaleString()}`;
        topExpensesList.appendChild(li);
    });
}

// Add sample data (optional, can be removed)
function addSampleData() {
    expenses = [
        { category: 'Groceries', amount: 15000 },
        { category: 'Rent', amount: 40000 },
        { category: 'Transportation', amount: 5000 },
        { category: 'Entertainment', amount: 10000 },
        { category: 'Communication', amount: 2000 },
        { category: 'Gym', amount: 3000 }
    ];
    updateExpenseTable();
}

// Load sample data when the page loads
window.onload = addSampleData; 