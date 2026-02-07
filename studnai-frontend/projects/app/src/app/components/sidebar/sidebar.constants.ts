import { ModuleIconType } from './sidebar.types';

export const MODULE_ICONS: Record<ModuleIconType, string> = {
  MATHEMATICS: 'hugeCalculator', // ou 'saxCalculator'
  PHYSICS: 'hugeAtom02', // ou 'saxAtom'
  PROGRAMMING: 'hugeCode', // ou 'saxCode'
  SOFTWARE_ENGINEERING: 'hugeCpuCharge', // ou 'saxCpuCharge'
  CHEMISTRY: 'hugeTestTube', // ou 'saxTestTube'
  BIOLOGY: 'hugeDna', // ou 'saxDna'
  HISTORY: 'hugeBook02', // ou 'saxBook'
  LITERATURE: 'hugeBookOpen01', // ou 'saxBookOpen'
  ART: 'hugePaintBoard', // ou 'saxPaintBoard'
  MUSIC: 'hugeMusicNote01', // ou 'saxMusicNote'
  BUSINESS: 'hugeBriefcase01', // ou 'saxBriefcase'
  DEFAULT: 'hugeFolder01', // ou 'saxFolder'
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

export const FILE_TYPE_ICONS: Record<string, string> = {
  folder: 'hugeFolder01',
  file: 'hugeFile02',
  pdf: 'hugeDocumentAttachment',
  doc: 'hugeFileDoc',
  image: 'hugeImage01',
  video: 'hugeVideo01',
  audio: 'hugeMusicNote02',
};

export const ACTION_ICONS = {
  createFolder: 'hugeFolderAdd',
  createFile: 'hugeFileAdd',
  rename: 'hugeEdit02',
  delete: 'hugeDelete02',
  move: 'hugeFolderTransfer',
  copy: 'hugeCopy01',
  share: 'hugeShare08',
  info: 'hugeInformationCircle',
};
