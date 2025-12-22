import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { AuthApiService } from '../core/api/auth-api.service';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private authAPIService: AuthApiService) {}
  login(email: string, password: string) {
    const user: User = {
      email,
      password,
    };
    return this.authAPIService.login(user);
  }
}
