import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { NavigationEnd, Router } from "@angular/router";
import { IonicModule } from "@ionic/angular";
import { filter, startWith, Subject, switchMap } from "rxjs";
import { GroupCardComponent } from "src/app/components/group-card/group-card.component";
import { Group } from "src/app/models/group.model";
import { AuthService } from "src/app/services/auth.service";
import { GroupService } from "src/app/services/group-service";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, GroupCardComponent],
})
export class HomepageComponent implements OnInit {
  groups: Group[] = [];
  isLoading = false;
  userName: string = '';

  private refresh$ = new Subject<void>();
  private email: string = '';

  constructor(
    private router: Router,
    private groupService: GroupService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.email = localStorage.getItem('userEmail') || '';
    if (!this.email) return;

    // 🔥 Auto refresh groups
    this.isLoading = true;
    this.refresh$
      .pipe(
        startWith(void 0), // initial load
        switchMap(() => this.groupService.getGroups(this.email))
      )
      .subscribe({
        next: (res) => {
          this.groups = res || [];
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Failed to fetch groups', err);
          this.isLoading = false;
        },
      });

    // 👤 Load username once
    this.authService.findUserByEmail(this.email).subscribe({
      next: (user) => {
        this.userName = user.name || '';
      },
    });

    // 👀 Listen to navigation back to homepage
    this.router.events
      .pipe(filter((e) => e instanceof NavigationEnd))
      .subscribe(() => {
        this.refresh$.next(); // 🔁 refresh groups
      });
  }

  createGroup() {
    this.router.navigateByUrl('/create-group');
  }

  openGroup(group?: Group) {
    if (!group || !group.groupId) return;

    this.groupService.getGroupDashboard(group.groupId).subscribe({
      next: (dashboard) => {
        this.groupService.setDashboardData(dashboard);
        this.router.navigateByUrl(`/${group.groupId}/dashboard`);
      },
    });
  }
}
