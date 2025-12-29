import { Component } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { FirebaseService } from 'src/app/services/firebase.service';
import { FirebaseAuthService } from 'src/app/core/api/firebase/auth.firebase';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
})
export class LoginPage {
  email: string = '';
  password: string = '';
  isLoginClicked: boolean = false;
  isCreatingUser: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    if (this.isLoginClicked) return;
    this.isLoginClicked = true;
    if (!this.email || !this.password) return;
    console.log('Logging in with', this.email);
    this.authService.login(this.email, this.password).subscribe(
      (res) => {
        console.log(res);
        localStorage.setItem('auth_token', res.token);
        this.router.navigateByUrl('/homepage');
      },
      (err) => {
        console.error('login failed', err);
      }
    );
    localStorage.setItem('userEmail', this.email);
  }
  createUser() {
    if (this.isCreatingUser) return;
    this.isCreatingUser = true;
    console.log('Registering new User');
    this.router.navigateByUrl('/register-user');
  }
  ionViewWillEnter() {
    this.isLoginClicked = false;
    this.isCreatingUser = false;
  }
}
