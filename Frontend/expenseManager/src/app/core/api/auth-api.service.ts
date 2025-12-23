import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthApiService {
  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}
  login(user: User) {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, user);
  }
  createUser(user: User) {
    return this.http.post<SignupResponse>(`${this.baseUrl}/create-user`, user);
  }
}

export interface LoginResponse {
  token: string;
}
export interface SignupResponse {
  token: string;
  user: User;
}
