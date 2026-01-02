export interface GroupDashboard {
    groupId: string;
    groupName: string;
    members: string[];
    totalExpenses: number;
    balances: { [member: string]: number };
    settlements: Settlement[];
    expenses: Expense[];
}

export interface Settlement {
    from: string;
    to: string;
    amount: number;
}

export interface Expense {
    expenseId: string;
    groupId: string;
    paidBy: string;
    amount: number;
    splitBetween: string[];
    description: string;
    createdAt: string;
}