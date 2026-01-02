import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { GroupService } from 'src/app/services/group-service';
import { ExpenseService } from 'src/app/services/expense-service';

@Component({
  selector: 'app-group-dashboard',
  templateUrl: './group-dashboard.component.html',
  styleUrls: ['./group-dashboard.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
})
export class GroupDashboardComponent implements OnInit {
  groupId?: string;
  dashboard: any;
  isLoading = false;

  // Add expense UI state
  showAddExpense = false;
  newExpenseAmount: number | null = null;
  splitType: 'equal' | 'between' = 'equal';
  selectedMembersMap: { [member: string]: boolean } = {};
  isSubmitting = false;

  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService,
    private expenseService: ExpenseService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('groupId');
    if (id) {
      this.groupId = id;
      this.loadDashboard(id);
    }
  }

  private loadDashboard(id: string) {
    this.isLoading = true;
    this.groupService.getGroupDashboard(id).subscribe({
      next: (dashboard) => {
        this.dashboard = dashboard;
        this.isLoading = false;
        // Initialize selection map for members
        this.selectedMembersMap = {};
        (dashboard?.members || []).forEach(
          (m: string) => (this.selectedMembersMap[m] = false)
        );
        localStorage.setItem(
          'currentGroupDashboard',
          JSON.stringify(dashboard)
        );
      },
      error: (err) => {
        console.error('Failed to fetch group dashboard', err);
        this.isLoading = false;
      },
    });
  }

  openAddExpense() {
    this.newExpenseAmount = null;
    this.splitType = 'equal';
    // reset map
    Object.keys(this.selectedMembersMap).forEach(
      (k) => (this.selectedMembersMap[k] = false)
    );
    this.showAddExpense = true;
  }

  closeAddExpense() {
    this.showAddExpense = false;
  }

  toggleMemberSelection(member: string) {
    this.selectedMembersMap[member] = !this.selectedMembersMap[member];
  }

  private getSelectedMembers(): string[] {
    return Object.keys(this.selectedMembersMap).filter(
      (k) => this.selectedMembersMap[k]
    );
  }

  canAddExpense(): boolean {
    if (!this.newExpenseAmount || this.newExpenseAmount <= 0) return false;
    if (this.splitType === 'between')
      return this.getSelectedMembers().length > 0;
    return true;
  }

  confirmAddExpense() {
    if (!this.groupId) return;
    if (!this.canAddExpense()) return;

    const paidBy = localStorage.getItem('userEmail') || '';
    const splitBetween =
      this.splitType === 'equal'
        ? this.dashboard?.members || []
        : this.getSelectedMembers();

    const expense = {
      expenseId: '',
      groupId: this.groupId,
      paidBy,
      amount: Number(this.newExpenseAmount),
      splitBetween,
      description: '',
      createdAt: '',
    };

    this.isSubmitting = true;
    this.expenseService.addExpense(expense as any).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.closeAddExpense();
        // Refresh dashboard to show updated expenses/balances
        if (this.groupId) this.loadDashboard(this.groupId);
      },
      error: (err) => {
        console.error('Failed to add expense', err);
        this.isSubmitting = false;
      },
    });
  }
}
