// header.component.ts
import {
  FILE_TYPE_ICONS,
  MODULE_COLORS,
  MODULE_ICONS,
} from '@/components/sidebar/sidebar.constants';
import { ModuleIconType } from '@/components/sidebar/sidebar.types';
import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatMenuModule } from '@angular/material/menu';
import { appIcons } from '@core/configs/app.icons';
import { BreadcrumbItem, NavigationService } from '@core/services/navigation.service';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { solarMenuDotsBoldDuotone } from '@ng-icons/solar-icons/bold-duotone';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, NgIconComponent, MatMenuModule, MatDividerModule, MatButtonModule],
  templateUrl: './home-header.component.html',
  styleUrls: ['./home-header.component.scss'],
  viewProviders: [
    provideIcons({
      ...appIcons,
      solarMenuDotsBoldDuotone,
    }),
  ],
})
export class HomeHeaderComponent {
  public navigationService = inject(NavigationService);

  public getItemIcon(crumb: BreadcrumbItem): string {
    if (crumb.icon && crumb.type === 'folder') {
      return MODULE_ICONS[crumb.icon as ModuleIconType] || MODULE_ICONS.DEFAULT;
    }

    switch (crumb.type) {
      case 'folder':
        return FILE_TYPE_ICONS['folder'];
      case 'pdf':
        return FILE_TYPE_ICONS['pdf'];
      case 'file':
        return FILE_TYPE_ICONS['file'];
      default:
        return FILE_TYPE_ICONS['file'];
    }
  }

  public getItemColor(crumb: BreadcrumbItem): string {
    if (crumb.icon && crumb.type === 'folder') {
      return MODULE_COLORS[crumb.icon as ModuleIconType] || MODULE_COLORS.DEFAULT;
    }

    switch (crumb.type) {
      case 'pdf':
        return 'text-red-500 dark:text-red-400';
      case 'file':
        return 'text-gray-500 dark:text-gray-400';
      case 'folder':
        return 'text-gray-600 dark:text-gray-400';
      default:
        return 'text-gray-500 dark:text-gray-400';
    }
  }

  public getItemTypeLabel(type: string): string {
    const labels: Record<string, string> = {
      folder: 'Pasta',
      pdf: 'PDF',
      file: 'Arquivo',
    };
    return labels[type] || type;
  }

  public onBreadcrumbClick(crumb: BreadcrumbItem): void {
    console.log('Navegar para:', crumb);
  }

  public onAddClick(): void {
    const currentItem = this.navigationService.currentItem();
    if (!currentItem || currentItem.type !== 'folder') return;

    console.log('Adicionar pasta em:', currentItem);
  }

  public onAddFileClick(): void {
    const currentItem = this.navigationService.currentItem();
    if (!currentItem || currentItem.type !== 'folder') return;

    console.log('Adicionar arquivo em:', currentItem);
    // TODO: Abrir dialog para upload de arquivo
  }

  public onRenameClick(): void {
    const currentItem = this.navigationService.currentItem();
    if (!currentItem) return;

    const newName = prompt('Novo nome:', currentItem.name);
    if (newName && newName !== currentItem.name) {
      console.log('Renomear para:', newName);
      // TODO: Chamar serviço para renomear
    }
  }

  public onDeleteClick(): void {
    const currentItem = this.navigationService.currentItem();
    if (!currentItem) return;

    if (confirm(`Mover "${currentItem.name}" para a lixeira?`)) {
      console.log('Deletar:', currentItem);
      // TODO: Chamar serviço para deletar
    }
  }
}
