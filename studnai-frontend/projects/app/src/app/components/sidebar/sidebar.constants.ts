import { ModuleIconType } from './sidebar.types';

export const MODULE_ICONS: Record<ModuleIconType, string> = {
  MATHEMATICS: 'solarCalculatorBold',
  PHYSICS: 'solarAtomBold',
  PROGRAMMING: 'solarCodeBold',
  SOFTWARE_ENGINEERING: 'saxCpuBold', // Atualizado (Iconsax)
  CHEMISTRY: 'solarTestTubeBold',
  BIOLOGY: 'solarLeafBold', // Atualizado (Solar Leaf)
  HISTORY: 'solarBook2Bold',
  LITERATURE: 'solarBookBookmarkBold',
  ART: 'solarPaletteBold',
  MUSIC: 'solarMusicLibrary2Bold',
  BUSINESS: 'solarCaseBold',
  DEFAULT: 'solarFolderBold',
};

export const FILE_TYPE_ICONS: Record<string, string> = {
  folder: 'solarFolderBold',
  file: 'solarFileTextBold',
  pdf: 'solarDocumentTextBold', // Ou solarFileTextBold
  doc: 'solarFileTextBold',
  image: 'solarGalleryBold',
  video: 'solarVideocameraRecordBold',
  audio: 'solarMusicNotesBold',
};

export const ACTION_ICONS = {
  // Usando Iconsax aqui para algumas ações específicas que ficam boas em Bold
  createFolder: 'saxFolderAddBold',
  createFile: 'saxDocumentText1Bold', // Representa adicionar arquivo bem
  rename: 'solarPenBold',
  delete: 'solarTrashBinMinimalisticBold',
  move: 'solarMoveToFolderBold', // Ou saxFolderConnectionBold
  copy: 'solarCopyBold',
  share: 'solarShareBold',
  info: 'solarInfoCircleBold',
};

export const MODULE_COLORS: Record<ModuleIconType, string> = {
  MATHEMATICS: 'text-blue-500 dark:text-blue-400',
  PHYSICS: 'text-purple-500 dark:text-purple-400',
  PROGRAMMING: 'text-green-500 dark:text-green-400',
  SOFTWARE_ENGINEERING: 'text-cyan-500 dark:text-cyan-400',
  CHEMISTRY: 'text-yellow-500 dark:text-yellow-400',
  BIOLOGY: 'text-emerald-500 dark:text-emerald-400',
  HISTORY: 'text-amber-500 dark:text-amber-400',
  LITERATURE: 'text-pink-500 dark:text-pink-400',
  ART: 'text-rose-500 dark:text-rose-400',
  MUSIC: 'text-violet-500 dark:text-violet-400',
  BUSINESS: 'text-orange-500 dark:text-orange-400',
  DEFAULT: 'text-gray-500 dark:text-gray-400',
};
