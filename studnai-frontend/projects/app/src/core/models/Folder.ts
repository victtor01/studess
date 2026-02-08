import { FileSystemNode } from './FileSystemNode';

export interface Folder extends FileSystemNode {
  icon: string;
  children: string;
  metadata: string;
}
