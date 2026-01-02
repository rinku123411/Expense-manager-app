import { Injectable } from '@angular/core';
import { GroupApiService } from '../core/api/group-api.service';
import { Group } from '../models/group.model';
import { GroupDashboard } from '../models/group-dashboard.model';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  private dashboardData: GroupDashboard = null as any;
  constructor(private groupApiService: GroupApiService) {}
  createGroup(name: string, members: string[]) {
    const group: Group = {
      name: name,
      membersId: members,
      createdBy: localStorage.getItem('userEmail') || '',
    };
    console.log(group);
    return this.groupApiService.createGroup(group);
    // Placeholder for future implementation
  }

  getGroups(email: string) {
    return this.groupApiService.getGroups(email);
  }
  getGroupDashboard(groupId: string) {
    return this.groupApiService.getGroupDashboard(groupId);
  }
  getDashboardData() {
    return this.dashboardData;
  }
  setDashboardData(data: GroupDashboard) {
    this.dashboardData = data;
  }
}
