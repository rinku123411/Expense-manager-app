import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { GroupService } from 'src/app/services/group-service';
import { ExpenseService } from 'src/app/services/expense-service';
import { AuthService } from 'src/app/services/auth.service';
import { forkJoin, of } from 'rxjs';
import {
  switchMap,
  catchError,
  map,
  finalize,
  tap,
  take,
  timeout,
} from 'rxjs/operators';

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
  currentUserEmail = localStorage.getItem('userEmail') || '';
  activeTab: 'expenses' | 'settlements' = 'expenses';

  // Add expense UI state
  showAddExpense = false;
  newExpenseAmount: number | null = null;
  newExpenseDescription: string = '';
  splitType: 'equal' | 'between' = 'equal';
  selectedMembersMap: { [member: string]: boolean } = {};
  isSubmitting = false;
  // store objects with { name, email }
  members: { name: string; email: string }[] = [];
  private userNameCache = new Map<string, string>(); // cache to avoid repeated lookups

  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService,
    private expenseService: ExpenseService,
    private authService: AuthService
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
    this.groupService
      .getGroupDashboard(id)
      .pipe(
        switchMap((dashboard) => {
          this.dashboard = dashboard;
          const members = dashboard?.members || [];

          // Initialize selection map for members
          this.selectedMembersMap = {};
          members.forEach((m: string) => (this.selectedMembersMap[m] = false));

          if (members.length === 0) {
            return of({ dashboard, members: [] });
          }

          const currentUserEmail = localStorage.getItem('userEmail') || '';
          const requests = members.map((member: string) => {
            if (this.userNameCache.has(member)) {
              const cachedName = this.userNameCache.get(member)!;
              const name = member === currentUserEmail ? 'You' : cachedName;
              return of({
                name,
                email: member,
              });
            }
            return this.authService.findUserByEmail(member).pipe(
              timeout(3000), // guard slow lookups
              take(1), // ensure lookup completes so forkJoin can emit
              map((user) => ({
                name:
                  member === currentUserEmail ? 'You' : user?.name ?? member,
                email: member,
              })),
              catchError(() =>
                of({
                  name: member === currentUserEmail ? 'You' : member,
                  email: member,
                })
              ),
              tap((obj) => this.userNameCache.set(member, obj.name))
            );
          });

          return forkJoin(requests).pipe(
            map((memberObjs: { name: string; email: string }[]) => ({
              dashboard,
              members: memberObjs,
            }))
          );
        }),
        tap(() => (this.isLoading = false)), // clear spinner on first value
        take(1), // complete after first emission so finalize runs
        finalize(() => (this.isLoading = false))
      )
      .subscribe({
        next: ({ dashboard, members }: any) => {
          this.members = members;

          // Build quick lookup map for email -> display name
          const emailToName = new Map<string, string>();
          (members || []).forEach((m: { name: string; email: string }) =>
            emailToName.set(m.email, m.name)
          );

          // Annotate expenses with display fields to avoid calling functions in template
          (dashboard?.expenses || []).forEach((e: any) => {
            e.paidByDisplay =
              e.paidBy === this.currentUserEmail
                ? 'You'
                : emailToName.get(e.paidBy) || e.paidBy;
            e.splitBetweenDisplay = (e.splitBetween || []).map((id: string) =>
              id === this.currentUserEmail ? 'You' : emailToName.get(id) || id
            );
          });

          // Annotate settlements
          (dashboard?.settlements || []).forEach((s: any) => {
            s.toDisplay =
              s.to === this.currentUserEmail
                ? 'You'
                : emailToName.get(s.to) || s.to;
            s.fromDisplay =
              s.from === this.currentUserEmail
                ? 'You'
                : emailToName.get(s.from) || s.from;
          });

          // Initialize selection map using member emails (consistent keys)
          this.selectedMembersMap = {};
          (this.members || []).forEach(
            (m) => (this.selectedMembersMap[m.email] = false)
          );

          // Cache the dashboard and set it on the service for quick access
          this.groupService.setDashboardData(dashboard);
          localStorage.setItem(
            'currentGroupDashboard',
            JSON.stringify(dashboard)
          );

          this.dashboard = dashboard;
        },
        error: (err) => {
          console.error('Failed to fetch group dashboard', err);
          this.isLoading = false; // extra safety
        },
      });
  }

  openAddExpense() {
    this.newExpenseAmount = null;
    this.newExpenseDescription = '';
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

  // Return selected members' display names (falls back to email if name not available)
  private getSelectedMemberNames(): string[] {
    const selectedEmails = this.getSelectedMembers();
    return selectedEmails.map((email) => {
      const mem = this.members.find((m) => m.email === email);
      return mem ? mem.name : email;
    });
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
        : this.getSelectedMemberNames();

    const expense = {
      expenseId: '',
      groupId: this.groupId,
      paidBy,
      amount: Number(this.newExpenseAmount),
      splitBetween,
      description: this.newExpenseDescription,
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
  goBack() {
    window.history.back();
  }
}
