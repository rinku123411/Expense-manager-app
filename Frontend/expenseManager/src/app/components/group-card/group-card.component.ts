import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Group } from 'src/app/models/group.model';

@Component({
  selector: 'app-group-card',
  templateUrl: './group-card.component.html',
  styleUrls: ['./group-card.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule],
})
export class GroupCardComponent {
  @Input() group?: Group;
  @Output() select = new EventEmitter<Group | undefined>();

  get memberCount(): number {
    return this.group?.membersId?.length ?? 0;
  }

  get createdAtDisplay(): string {
    if (!this.group?.createdAt) return '';
    try {
      return new Date(this.group.createdAt).toLocaleDateString();
    } catch (e) {
      return this.group.createdAt;
    }
  }

  onClick() {
    this.select.emit(this.group);
  }
}
