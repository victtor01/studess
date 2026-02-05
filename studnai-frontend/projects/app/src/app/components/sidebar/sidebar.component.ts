// sidebar.component.ts
import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { ThemeService } from '@core/services/theme.service';
import { LogoComponent } from '../logo/logo.component';

interface Module {
  id: string;
  name: string;
  subs: string[];
  isOpen?: boolean;
}

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, LogoComponent],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent {
  public themeService = inject(ThemeService);

  public modules = signal<Module[]>([
    {
      id: '1',
      name: 'Matemática',
      isOpen: true,
      subs: ['Álgebra Linear', 'Cálculo I', 'Geometria Analítica', 'Estatística'],
    },
    {
      id: '2',
      name: 'Programação',
      isOpen: false,
      subs: ['JavaScript', 'TypeScript', 'Angular', 'React', 'Node.js'],
    },
    {
      id: '3',
      name: 'Física',
      isOpen: false,
      subs: ['Mecânica Clássica', 'Termodinâmica', 'Eletromagnetismo', 'Física Quântica'],
    },
    {
      id: '4',
      name: 'Engenharia de Software',
      isOpen: false,
      subs: ['Design Patterns', 'Clean Architecture', 'TDD', 'DevOps', 'Microservices'],
    },
  ]);

  public addModule(module: Module): void {
    this.modules.update((current) => [...current, module]);
  }

  public removeModule(id: string): void {
    this.modules.update((current) => current.filter((m) => m.id !== id));
  }

  public updateModule(id: string, updates: Partial<Module>): void {
    this.modules.update((current) => current.map((m) => (m.id === id ? { ...m, ...updates } : m)));
  }
}
