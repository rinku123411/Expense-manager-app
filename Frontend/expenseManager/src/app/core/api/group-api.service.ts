import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupDashboard } from 'src/app/models/group-dashboard.model';
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

  getGroups(email: string) {
    return this.http.get<Group[]>(`${this.baseUrl}/groups`, {
      params: { email },
      headers: new HttpHeaders({
        'X-SKIP-LOADER': 'true',
      }),
    });
  }
  getGroupDashboard(groupId: string): Observable<GroupDashboard> {
    return this.http.get<GroupDashboard>(
      `${this.baseUrl}/groups/${groupId}/dashboard`,
      {
        headers: new HttpHeaders({
          'X-SKIP-LOADER': 'true',
        }),
      }
    );
  }
}
