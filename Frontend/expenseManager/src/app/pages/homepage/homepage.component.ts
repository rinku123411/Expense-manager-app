import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NavController, IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';
import { GroupService } from 'src/app/services/group-service';
import { Group } from 'src/app/models/group.model';
import { GroupCardComponent } from 'src/app/components/group-card/group-card.component';
import { User } from 'firebase/auth';
import { AuthService } from 'src/app/services/auth.service';

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

  constructor(
    private router: Router,
    private groupService: GroupService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const email = localStorage.getItem('userEmail') || '';
    if (email) {
      this.isLoading = true;
      this.groupService.getGroups(email).subscribe({
        next: (res) => {
          this.groups = res || [];
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Failed to fetch groups', err);
          this.isLoading = false;
        },
      });
      this.authService.findUserByEmail(email).subscribe({
        next: (user) => {
          this.userName = user.name || '';
        },
      });
    }
  }

  createGroup() {
    console.log('createGroup clicked');
    this.router.navigateByUrl('/create-group');
  }

  openGroup(group?: Group) {
    if (!group || !group.groupId) return;
    console.log('Group clicked', group.groupId);
    this.groupService.getGroupDashboard(group.groupId).subscribe({
      next: (dashboard) => {
        console.log('Fetched group dashboard', dashboard);
        this.groupService.setDashboardData(dashboard);
        console.log('Navigating to group dashboard');
        this.router.navigateByUrl(`/${group.groupId}/dashboard`);
      },
    });
  }
}
