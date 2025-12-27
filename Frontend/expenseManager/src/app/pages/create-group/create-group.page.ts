import { Component } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

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
  members: Array<{ userId: string; name?: string; email: string }> = [];
  errorMessage: string = '';
  isAdding: boolean = false;

  constructor(private router: Router, private authService: AuthService) {}

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
            userId: user.userId?.toString() || '',
            name: user.name || user.email,
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

  get memberIds() {
    return this.members.map((m) => m.userId);
  }

  createGroup() {
    console.log('Creating group', this.groupName, this.memberIds);
    // TODO: replace with real create logic (API call / firebase etc.)
    this.router.navigateByUrl('/homepage');
  }

  cancel() {
    this.router.navigateByUrl('/homepage');
  }
}
