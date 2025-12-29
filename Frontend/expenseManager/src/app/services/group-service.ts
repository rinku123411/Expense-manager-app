import { Injectable } from '@angular/core';
import { GroupApiService } from '../core/api/group-api.service';
import { Group } from '../models/group.model';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  constructor(private groupApiService: GroupApiService) {}
  createGroup(name: string, members: string[]) {
    const group: Group = {
      name: name,
      membersEmail: members,
      createdBy: localStorage.getItem('userEmail') || '',
    };
    console.log(group);
    return this.groupApiService.createGroup(group);
    // Placeholder for future implementation
  }
}
