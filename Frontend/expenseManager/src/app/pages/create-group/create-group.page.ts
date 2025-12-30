import { Component } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { GroupService } from 'src/app/services/group-service';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.page.html',
  styleUrls: ['./create-group.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
})
export class CreateGroupPage {
  groupName: string = '';
  memberEmail: string = '';
  members: Array<{ email: string }> = [];
  errorMessage: string = '';
  isAdding: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private groupService: GroupService
  ) {}

  addMember() {
    const email = this.memberEmail ? this.memberEmail.trim() : '';
    if (!email) {
      this.errorMessage = 'Please enter an email';
      return;
    }

    if (
      this.members.some((m) => m.email.toLowerCase() === email.toLowerCase())
    ) {
      this.errorMessage = 'Member already added';
      return;
    }

    this.isAdding = true;
    this.errorMessage = '';

    this.authService.findUserByEmail(email).subscribe({
      next: (user) => {
        if (!user || !user.userId) {
          this.errorMessage = 'User not found';
        } else {
          this.members.push({
            email: user.email,
          });
          this.memberEmail = '';
        }
        this.isAdding = false;
      },
      error: (err) => {
        console.error('findUserByEmail failed', err);
        this.errorMessage = 'User not found';
        this.isAdding = false;
      },
    });
  }

  removeMember(index: number) {
    this.members.splice(index, 1);
  }

  // Return list of member emails (not user IDs)
  get memberIds() {
    return this.members.map((m) => m.email);
  }

  isCreating: boolean = false;

  createGroup() {
    if (this.isCreating) return;
    this.isCreating = true;

    const userEmail = localStorage.getItem('userEmail');
    if (userEmail) {
      this.members.push({ email: userEmail });
    }
    console.log('Creating group', this.groupName, this.memberIds);
    this.groupService.createGroup(this.groupName, this.memberIds).subscribe(
      (res) => {
        console.log('Group created successfully', res);
        // navigate after successful creation
        this.router.navigateByUrl('/homepage');
        this.isCreating = false;
      },
      (err) => {
        console.error('Failed to create group', err);
        this.isCreating = false;
      }
    );
  }

  cancel() {
    this.router.navigateByUrl('/homepage');
  }
}
