import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Group } from 'src/app/models/group.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GroupApiService {
  private baseUrl = environment.apiBaseUrl;
  constructor(private http: HttpClient) {}
  createGroup(group: Group) {
    return this.http.post<string>(`${this.baseUrl}/create-group`, group);
  }
}
