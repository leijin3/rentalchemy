let {
    maintenances,
    incomes,
    appliances,
    expenses,
    tenants,
    properties,
    users,
} = require('./db.json');

maintenances = maintenances.map((maintenance) => ({
    year: new Date(maintenance.date_finished).getFullYear(),
    month: new Date(maintenance.date_finished).getMonth() + 1,
    ...maintenance,
}));

incomes = incomes.map((income) => ({
    year: new Date(income.date_received).getFullYear(),
    month: new Date(income.date_received).getMonth() + 1,
    ...income,
}));

appliances = appliances.map((appliance) => ({
    year: new Date(appliance.date_purchased).getFullYear(),
    month: new Date(appliance.date_received).getMonth() + 1,
    ...appliance,
}));

expenses = expenses.map((expense) => ({
    year: new Date(expense.date_spent).getFullYear(),
    month: new Date(expense.date_spent).getMonth() + 1,
    ...expense,
}));

properties = properties.map((property) => ({
    year: new Date(property.date_acquired).getFullYear(),
    month: new Date(property.date_acquired).getMonth() + 1,
    ...property,
}));

module.exports = {
    maintenances,
    incomes,
    appliances,
    expenses,
    tenants,
    properties,
    users,
};
