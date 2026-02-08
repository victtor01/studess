// navigation.service.ts
import { FolderItem } from '@/components/sidebar/sidebar.types';
import { computed, Injectable, signal } from '@angular/core';

export interface BreadcrumbItem {
  id: string;
  name: string;
  type: 'folder' | 'file' | 'pdf';
  icon?: string;
}

@Injectable({
  providedIn: 'root',
})
export class NavigationService {
  // Item atualmente selecionado
  private _currentItem = signal<FolderItem | null>(null);

  // Path completo até o item atual
  private _breadcrumbs = signal<BreadcrumbItem[]>([]);

  // Exposição pública read-only
  public currentItem = this._currentItem.asReadonly();
  public breadcrumbs = this._breadcrumbs.asReadonly();

  /**
   * Breadcrumb formatado com limitação para UX
   * Se houver mais de 3 itens, mostra: primeiro / ... / últimos 2
   */
  public formattedBreadcrumbs = computed(() => {
    const crumbs = this._breadcrumbs();

    if (crumbs.length <= 3) {
      return crumbs;
    }

    // Retorna: [primeiro, ..., penúltimo, último]
    return [
      crumbs[0],
      { id: 'ellipsis', name: '...', type: 'folder' as const },
      crumbs[crumbs.length - 2],
      crumbs[crumbs.length - 1],
    ];
  });

  /**
   * Define o item atual e calcula o breadcrumb
   */
  public setCurrentItem(item: FolderItem | null, allItems: FolderItem[]): void {
    this._currentItem.set(item);

    if (!item) {
      this._breadcrumbs.set([]);
      return;
    }

    // Calcular path completo
    const path = this.findPath(allItems, item.id);
    this._breadcrumbs.set(path);
  }

  /**
   * Encontra o caminho completo até um item recursivamente
   */
  private findPath(
    items: FolderItem[],
    targetId: string,
    currentPath: BreadcrumbItem[] = [],
  ): BreadcrumbItem[] {
    for (const item of items) {
      const newPath = [
        ...currentPath,
        {
          id: item.id,
          name: item.name,
          type: item.type,
          icon: item.icon,
        },
      ];

      // Encontrou o item
      if (item.id === targetId) {
        return newPath;
      }

      // Buscar nos filhos
      if (item.children && item.children.length > 0) {
        const found = this.findPath(item.children, targetId, newPath);
        if (found.length > 0) {
          return found;
        }
      }
    }

    return [];
  }

  /**
   * Limpa a seleção atual
   */
  public clearSelection(): void {
    this._currentItem.set(null);
    this._breadcrumbs.set([]);
  }
}
