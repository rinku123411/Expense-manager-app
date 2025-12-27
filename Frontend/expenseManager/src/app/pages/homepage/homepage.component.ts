import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NavController, IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule],
})
export class HomepageComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit() {}

  createGroup() {
    console.log('createGroup clicked');
    this.router.navigateByUrl('/create-group');
  }
}
