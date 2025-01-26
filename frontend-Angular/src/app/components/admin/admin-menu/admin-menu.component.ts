import { Component } from '@angular/core';
import { Route, RouterLink } from '@angular/router';


@Component({
  selector: 'app-admin-menu',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './admin-menu.component.html',
  styleUrl: './admin-menu.component.css'
})
export class AdminMenuComponent {

}
