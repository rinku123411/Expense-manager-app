import { Component, OnInit } from '@angular/core';
import { NavController, IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
})
export class RegisterUserComponent implements OnInit {
  // form model
  name = '';
  email = '';
  password = '';
  confirmPassword = '';

  // validation helpers
  submitted = false;
  errors: {
    required: string | null;
    email: string | null;
    password: string | null;
    confirmPassword: string | null;
    passwordMismatch: string | null;
  } = {
    required: null,
    email: null,
    password: null,
    confirmPassword: null,
    passwordMismatch: null,
  };

  constructor(
    private navCtrl: NavController,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {}

  /**
   * Navigate back using Ionic NavController.
   */
  goBack() {
    this.navCtrl.back();
  }

  onInput(field: string, value: string) {
    (this as any)[field] = value;

    // live-check passwords when typing
    if (field === 'password' || field === 'confirmPassword') {
      this.checkPasswordsMatch();
    }

    // clear errors as user types
    this.submitted = false;
    this.errors = {
      required: null,
      email: null,
      password: null,
      confirmPassword: null,
      passwordMismatch: null,
    };
  }

  checkPasswordsMatch() {
    if (
      this.password &&
      this.confirmPassword &&
      this.password !== this.confirmPassword
    ) {
      this.errors.passwordMismatch = 'Passwords do not match.';
      return false;
    }
    this.errors.passwordMismatch = null;
    return true;
  }

  validate() {
    this.errors.required = null;
    this.errors.email = null;
    this.errors.password = null;
    this.errors.confirmPassword = null;

    if (!this.name || !this.email || !this.password || !this.confirmPassword) {
      this.errors.required = 'All fields are required.';
    }

    // basic email regex
    const emailRe = /^\S+@\S+\.\S+$/;
    if (this.email && !emailRe.test(this.email)) {
      this.errors.email = 'Please enter a valid email address.';
    }

    if (this.password && this.password.length < 6) {
      this.errors.password = 'Password must be at least 6 characters.';
    }

    if (!this.confirmPassword) {
      this.errors.confirmPassword = 'Please re-enter your password.';
    }

    this.checkPasswordsMatch();

    // form is valid when no error messages exist
    const hasError = Object.values(this.errors).some((v) => v !== null);
    return !hasError;
  }

  formValid() {
    // quick client-side enable/disable; do not rely on it on server
    if (!this.name || !this.email || !this.password || !this.confirmPassword) {
      return false;
    }
    if (!this.checkPasswordsMatch()) {
      return false;
    }
    if (this.password.length < 6) {
      return false;
    }
    const emailRe = /^\S+@\S+\.\S+$/;
    if (!emailRe.test(this.email)) {
      return false;
    }
    return true;
  }

  onSubmit(event: Event) {
    event.preventDefault();
    this.submitted = true;

    if (!this.validate()) {
      return;
    }

    console.log('register', { name: this.name, email: this.email });
    this.authService.createUser(this.name, this.email, this.password).subscribe(
      (res) => {
        localStorage.setItem('auth_token', res.token);
        console.log('User registered successfully', res);
        this.router.navigateByUrl('/homepage');
      },
      (err) => {
        console.error('User registration failed', err);
      }
    );
    localStorage.setItem('userEmail', this.email);
  }
}
