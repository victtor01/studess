// sidebar.component.ts (ATUALIZADO)
import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { appIcons } from '@core/configs/app.icons';
import { NavigationService } from '@core/services/navigation.service';
import { ThemeService } from '@core/services/theme.service';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { solarMenuDotsBoldDuotone } from '@ng-icons/solar-icons/bold-duotone';
import { CreateModuleDialogComponent } from '../home/create-folder/create-folder-dialog.component';
import { LogoComponent } from '../logo/logo.component';
import { ACTION_ICONS, FILE_TYPE_ICONS, MODULE_COLORS, MODULE_ICONS } from './sidebar.constants';
import { ContextMenuAction, FolderItem } from './sidebar.types';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, LogoComponent, NgIconComponent],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  viewProviders: [provideIcons({ ...appIcons, solarMenuDotsBoldDuotone })],
})
export class SidebarComponent {
  public themeService = inject(ThemeService);
  public dialog = inject(MatDialog);
  // NOVO: Injetar NavigationService
  private navigationService = inject(NavigationService);

  public modules = signal<FolderItem[]>(this.getMockData());
  public activeContextMenu = signal<{ itemId: string; x: number; y: number } | null>(null);
  public selectedItem = signal<string | null>(null);

  public readonly MODULE_ICONS = MODULE_ICONS;
  public readonly MODULE_COLORS = MODULE_COLORS;
  public readonly FILE_TYPE_ICONS = FILE_TYPE_ICONS;
  public readonly sidebarWidth = signal<number>(280);

  private isResizing = false;
  private startX = 0;
  private startWidth = 0;

  private readonly MIN_WIDTH = 200;
  private readonly MAX_WIDTH = 600;
  private readonly STORAGE_KEY = 'sidebar-width';

  constructor() {
    this.loadSavedWidth();

    if (typeof window !== 'undefined') {
      window.addEventListener('click', () => {
        this.activeContextMenu.set(null);
      });

      window.addEventListener('mousemove', this.onMouseMove.bind(this));
      window.addEventListener('mouseup', this.stopResize.bind(this));
    }
  }

  ngOnDestroy() {
    if (typeof window !== 'undefined') {
      window.removeEventListener('mousemove', this.onMouseMove.bind(this));
      window.removeEventListener('mouseup', this.stopResize.bind(this));
    }
  }

  public startResize(event: MouseEvent): void {
    event.preventDefault();
    this.isResizing = true;
    this.startX = event.clientX;
    this.startWidth = this.sidebarWidth();

    document.body.style.cursor = 'ew-resize';
    document.body.style.userSelect = 'none';
  }

  private onMouseMove(event: MouseEvent): void {
    if (!this.isResizing) return;

    const delta = event.clientX - this.startX;
    const newWidth = this.startWidth + delta;

    const clampedWidth = Math.max(this.MIN_WIDTH, Math.min(this.MAX_WIDTH, newWidth));

    this.sidebarWidth.set(clampedWidth);
  }

  private stopResize(): void {
    if (!this.isResizing) return;

    this.isResizing = false;

    document.body.style.cursor = '';
    document.body.style.userSelect = '';

    this.saveWidth();
  }

  private saveWidth(): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.setItem(this.STORAGE_KEY, this.sidebarWidth().toString());
    }
  }

  private loadSavedWidth(): void {
    if (typeof localStorage !== 'undefined') {
      const savedWidth = localStorage.getItem(this.STORAGE_KEY);
      if (savedWidth) {
        const width = parseInt(savedWidth, 10);
        if (!isNaN(width) && width >= this.MIN_WIDTH && width <= this.MAX_WIDTH) {
          this.sidebarWidth.set(width);
        }
      }
    }
  }

  private getMockData(): FolderItem[] {
    return [
      {
        id: '1',
        name: 'Matemática',
        type: 'folder',
        icon: 'MATHEMATICS',
        isOpen: true,
        children: [
          {
            id: '1-1',
            name: 'Álgebra Linear',
            icon: 'HISTORY',
            type: 'folder',
            isOpen: false,
            children: [
              {
                id: '1-1-1',
                name: 'Vetores.pdf',
                type: 'pdf',
              },
              {
                id: '1-1-2',
                name: 'Matrizes.pdf',
                type: 'pdf',
              },
              {
                id: '1-1-3',
                name: 'Exercícios',
                type: 'folder',
                children: [
                  { id: '1-1-3-1', name: 'Lista 1.pdf', type: 'pdf' },
                  { id: '1-1-3-2', name: 'Lista 2.pdf', type: 'pdf' },
                ],
              },
            ],
          },
          {
            id: '1-2',
            name: 'Cálculo I',
            type: 'folder',
            isOpen: false,
            children: [
              { id: '1-2-1', name: 'Limites.pdf', type: 'pdf' },
              { id: '1-2-2', name: 'Derivadas.pdf', type: 'pdf' },
              { id: '1-2-3', name: 'Integrais.pdf', type: 'pdf' },
            ],
          },
          {
            id: '1-3',
            name: 'Resumo Geral.pdf',
            type: 'pdf',
          },
        ],
      },
      {
        id: '2',
        name: 'Programação',
        type: 'folder',
        icon: 'PROGRAMMING',
        isOpen: false,
        children: [
          {
            id: '2-1',
            name: 'JavaScript',
            type: 'folder',
            children: [
              { id: '2-1-1', name: 'ES6 Features.pdf', type: 'pdf' },
              { id: '2-1-2', name: 'Async-Await.pdf', type: 'pdf' },
            ],
          },
          {
            id: '2-2',
            name: 'TypeScript',
            type: 'folder',
            children: [
              { id: '2-2-1', name: 'Types.pdf', type: 'pdf' },
              { id: '2-2-2', name: 'Interfaces.pdf', type: 'pdf' },
            ],
          },
          {
            id: '2-3',
            name: 'Projetos',
            type: 'folder',
            children: [
              { id: '2-3-1', name: 'projeto-1.zip', type: 'file' },
              { id: '2-3-2', name: 'projeto-2.zip', type: 'file' },
            ],
          },
        ],
      },
      {
        id: '3',
        name: 'Física',
        type: 'folder',
        icon: 'PHYSICS',
        isOpen: false,
        children: [
          {
            id: '3-1',
            name: 'Mecânica Clássica',
            type: 'folder',
            children: [
              { id: '3-1-1', name: 'Cinemática.pdf', type: 'pdf' },
              { id: '3-1-2', name: 'Dinâmica.pdf', type: 'pdf' },
            ],
          },
          { id: '3-2', name: 'Termodinâmica.pdf', type: 'pdf' },
        ],
      },
      {
        id: '4',
        name: 'Engenharia de Software',
        type: 'folder',
        icon: 'SOFTWARE_ENGINEERING',
        isOpen: false,
        children: [
          { id: '4-1', name: 'Design Patterns.pdf', type: 'pdf' },
          { id: '4-2', name: 'Clean Architecture.pdf', type: 'pdf' },
          {
            id: '4-3',
            name: 'Projetos',
            type: 'folder',
            children: [
              { id: '4-3-1', name: 'DDD.pdf', type: 'pdf' },
              { id: '4-3-2', name: 'SOLID.pdf', type: 'pdf' },
            ],
          },
        ],
      },
    ];
  }

  public getItemIcon(item: FolderItem): string {
    if (item.icon && item.type === 'folder') {
      return MODULE_ICONS[item.icon] || MODULE_ICONS.DEFAULT;
    }

    switch (item.type) {
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

  public getItemColor(item: FolderItem): string {
    if (item.icon && item.type === 'folder') {
      return MODULE_COLORS[item.icon] || MODULE_COLORS.DEFAULT;
    }

    switch (item.type) {
      case 'pdf':
        return 'text-red-500 dark:text-red-400';
      case 'file':
        return 'text-gray-500 dark:text-gray-400';
      default:
        return 'text-on-surface-400';
    }
  }

  public toggleFolder(item: FolderItem): void {
    if (item.type !== 'folder') return;

    this.updateItemRecursively(this.modules(), item.id, {
      isOpen: !item.isOpen,
    });
  }

  // ATUALIZADO: Notificar NavigationService quando selecionar item
  public selectItem(item: FolderItem, event: Event): void {
    event.stopPropagation();
    this.selectedItem.set(item.id);

    // NOVO: Atualizar NavigationService com o item selecionado
    this.navigationService.setCurrentItem(item, this.modules());

    if (item.type !== 'folder') {
      console.log('Abrir arquivo:', item);
    }
  }

  public openContextMenu(event: MouseEvent, item: FolderItem): void {
    event.preventDefault();
    event.stopPropagation();

    const rect = (event.target as HTMLElement).getBoundingClientRect();

    this.activeContextMenu.set({
      itemId: item.id,
      x: rect.right,
      y: rect.top,
    });
  }

  public getContextMenuActions(item: FolderItem): ContextMenuAction[] {
    const actions: ContextMenuAction[] = [];

    if (item.type === 'folder') {
      actions.push({
        id: 'create-folder',
        label: 'Nova Pasta',
        icon: ACTION_ICONS.createFolder,
        action: (item) => this.createSubfolder(item),
      });

      actions.push({
        id: 'create-file',
        label: 'Novo Arquivo',
        icon: ACTION_ICONS.createFile,
        action: (item) => this.createFile(item),
      });

      actions.push({
        id: 'separator-1',
        label: '',
        icon: '',
        action: () => {},
        separator: true,
      });
    }

    actions.push({
      id: 'rename',
      label: 'Renomear',
      icon: ACTION_ICONS.rename,
      action: (item) => this.renameItem(item),
    });

    actions.push({
      id: 'delete',
      label: 'Mover para Lixeira',
      icon: ACTION_ICONS.delete,
      action: (item) => this.deleteItem(item),
    });

    return actions;
  }

  private createSubfolder(parentItem: FolderItem): void {
    this.dialog.open(CreateModuleDialogComponent);
  }

  private createFile(parentItem: FolderItem): void {
    const newFile: FolderItem = {
      id: `${parentItem.id}-${Date.now()}`,
      name: 'Novo Arquivo.pdf',
      type: 'pdf',
    };

    this.addChildToItem(parentItem.id, newFile);
  }

  private renameItem(item: FolderItem): void {
    const newName = prompt('Novo nome:', item.name);
    if (newName) {
      this.updateItemRecursively(this.modules(), item.id, { name: newName });
      console.log('Item renomeado:', item.id, newName);
    }
  }

  private deleteItem(item: FolderItem): void {
    if (confirm(`Mover "${item.name}" para a lixeira?`)) {
      this.removeItemRecursively(this.modules(), item.id);
      console.log('Item deletado:', item.id);

      // NOVO: Limpar seleção se o item deletado estava selecionado
      if (this.selectedItem() === item.id) {
        this.navigationService.clearSelection();
        this.selectedItem.set(null);
      }
    }
  }

  private addChildToItem(parentId: string, newChild: FolderItem): void {
    const addRecursive = (items: FolderItem[]): FolderItem[] => {
      return items.map((item) => {
        if (item.id === parentId) {
          return {
            ...item,
            children: [...(item.children || []), newChild],
            isOpen: true,
          };
        }
        if (item.children) {
          return { ...item, children: addRecursive(item.children) };
        }
        return item;
      });
    };

    this.modules.update((items) => addRecursive(items));
  }

  private updateItemRecursively(
    items: FolderItem[],
    id: string,
    updates: Partial<FolderItem>,
  ): void {
    const updateRecursive = (items: FolderItem[]): FolderItem[] => {
      return items.map((item) => {
        if (item.id === id) {
          return { ...item, ...updates };
        }
        if (item.children) {
          return { ...item, children: updateRecursive(item.children) };
        }
        return item;
      });
    };

    this.modules.update((items) => updateRecursive(items));
  }

  private removeItemRecursively(items: FolderItem[], id: string): void {
    const removeRecursive = (items: FolderItem[]): FolderItem[] => {
      return items
        .filter((item) => item.id !== id)
        .map((item) => {
          if (item.children) {
            return { ...item, children: removeRecursive(item.children) };
          }
          return item;
        });
    };

    this.modules.update((items) => removeRecursive(items));
  }
}
