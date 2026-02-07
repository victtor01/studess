// sidebar.types.ts

export type ItemType = 'folder' | 'file' | 'pdf';

export type ModuleIconType =
  | 'MATHEMATICS'
  | 'PHYSICS'
  | 'PROGRAMMING'
  | 'SOFTWARE_ENGINEERING'
  | 'CHEMISTRY'
  | 'BIOLOGY'
  | 'HISTORY'
  | 'LITERATURE'
  | 'ART'
  | 'MUSIC'
  | 'BUSINESS'
  | 'DEFAULT';

export interface FolderItem {
  id: string;
  name: string;
  type: ItemType;
  icon?: ModuleIconType;
  isOpen?: boolean;
  children?: FolderItem[];
  createdAt?: Date;
  updatedAt?: Date;
  size?: number;
  path?: string;
}

export interface ContextMenuAction {
  id: string;
  label: string;
  icon: string;
  action: (item: FolderItem) => void;
  separator?: boolean;
}
