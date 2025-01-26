import { Component } from '@angular/core';
import { RouterLink,RouterLinkActive } from '@angular/router';
import { MenuEntry } from '../../shared/interfaces/menu-entry';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterLink,RouterLinkActive],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  menu: MenuEntry[] = [
    {text: "All Animals", routerLink:"all-animals"},
    {text: "Animals For Adoption", routerLink:"animals-for-adoption"}
  ]
}
