import { LoaderComponent } from '@/components/loader/loader.component';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CreateFolderDTO } from '@core/dtos/request/create-folder.dto';
import { createResourceSignal } from '@core/models/ResourceState';
import { FolderService } from '@core/services/folder.service';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { saxCpuBold } from '@ng-icons/iconsax/bold';
import {
  solarAtomBold,
  solarBook2Bold,
  solarBookBookmarkBold,
  solarCalculatorBold,
  solarCaseBold,
  solarCodeBold,
  solarFolderBold,
  solarLeafBold,
  solarMusicLibrary2Bold,
  solarPaletteBold,
  solarTestTubeBold,
} from '@ng-icons/solar-icons/bold';
import { firstValueFrom } from 'rxjs';

// Interface para o seletor
interface IconOption {
  label: string;
  iconName: string;
  colorClass: string;
}

@Component({
  imports: [CommonModule, FormsModule, NgIconComponent, LoaderComponent],
  viewProviders: [
    provideIcons({
      solarFolderBold,
      solarCalculatorBold,
      solarAtomBold,
      solarCodeBold,
      saxCpuBold,
      solarTestTubeBold,
      solarLeafBold,
      solarBook2Bold,
      solarBookBookmarkBold,
      solarPaletteBold,
      solarMusicLibrary2Bold,
      solarCaseBold,
    }),
  ],
  template: `
    <div
      class="w-105 font-sans bg-surface-50 text-gray-900 dark:text-gray-100 flex flex-col overflow-hidden rounded-xl border border-surface-200 shadow-2xl"
    >
      <div
        class="px-5 py-4 border-b border-surface-100 dark:border-surface-200 flex justify-between items-center bg-surface-100"
      >
        <div>
          <h2 class="text-lg font-semibold tracking-tight">Novo Módulo</h2>
          <p class="text-xs text-gray-500 dark:text-gray-400 mt-0.5">
            Crie uma nova área de organização.
          </p>
        </div>
        <button
          (click)="close()"
          class="text-gray-400 hover:text-gray-600 dark:hover:text-gray-200 transition-colors p-1 rounded hover:bg-gray-100 dark:hover:bg-surface-700"
        >
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M6 18L18 6M6 6l12 12"
            ></path>
          </svg>
        </button>
      </div>

      <div class="p-5 space-y-5">
        <div class="space-y-1.5">
          <label
            class="text-xs font-semibold text-gray-600 dark:text-gray-300 uppercase tracking-wide ml-1"
          >
            Nome do Módulo
          </label>
          <input
            type="text"
            [(ngModel)]="name"
            (keyup.enter)="create()"
            placeholder="Ex: Matemática Financeira"
            class="w-full px-3 py-2.5 bg-surface-100 border border-surface-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-primary-500/20 focus:border-primary-500 transition-all placeholder:text-gray-400"
            autoFocus
          />
        </div>

        <div class="space-y-2">
          <label
            class="text-xs font-semibold text-gray-600 dark:text-gray-300 uppercase tracking-wide ml-1"
          >
            Ícone & Cor
          </label>

          <div class="grid grid-cols-6 gap-2">
            @for (option of iconOptions; track option.iconName) {
              <button
                type="button"
                (click)="selectIcon(option)"
                class="group relative flex items-center justify-center p-2.5 rounded-lg border transition-all duration-200"
                [class.bg-primary-50]="selectedIcon() === option.iconName"
                [class.border-primary-200]="selectedIcon() === option.iconName"
                [class.dark:bg-primary-900/20]="selectedIcon() === option.iconName"
                [class.dark:border-primary-700]="selectedIcon() === option.iconName"
                [class.bg-white]="selectedIcon() !== option.iconName"
                [class.dark:bg-surface-50]="selectedIcon() !== option.iconName"
                [class.border-gray-200]="selectedIcon() !== option.iconName"
                [class.dark:border-surface-200]="selectedIcon() !== option.iconName"
                [class.hover:border-primary-300]="selectedIcon() !== option.iconName"
              >
                <ng-icon
                  [name]="option.iconName"
                  class="w-5 h-5 transition-transform group-hover:scale-110 duration-200"
                  [class]="
                    selectedIcon() === option.iconName
                      ? 'text-primary-600 dark:text-primary-400'
                      : 'text-gray-500 dark:text-gray-400'
                  "
                ></ng-icon>

                <span
                  class="absolute -bottom-8 bg-surface-200 text-white text-[10px] py-1 px-2 rounded opacity-0 group-hover:opacity-100 transition-opacity pointer-events-none z-10 whitespace-nowrap"
                >
                  {{ option.label }}
                </span>
              </button>
            }
          </div>
        </div>
      </div>

      @if (resource().error) {
        <div class="p-3 bg-error text-white">
          {{ resource().error }}
        </div>
      }

      <div class="px-5 py-4 bg-surface-100 border-t border-surface-200 flex justify-end gap-3">
        <button
          (click)="close()"
          class="px-4 py-2 text-sm font-medium text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-surface-700 rounded-lg transition-colors"
        >
          Cancelar
        </button>
        <button
          (click)="create()"
          [disabled]="!name()"
          [disabled]="resource().isLoading"
          [attr.data-loading]="resource().isLoading"
          class="px-4 py-2 data-[loading=true]:opacity-40 data-[loading=true]:cursor-default text-sm font-medium cursor-pointer text-surface-50 hover:bg-accent-100 hover:shadow-lg hover:shadow-accent-200/10 bg-accent-50 rounded-lg shadow-sm shadow-orange-500/20 disabled:opacity-50 disabled:cursor-not-allowed transition-all transform active:scale-95"
        >
          @if (resource().isLoading) {
            <app-loader-classic />
          } @else {
            Criar módulo
          }
        </button>
      </div>
    </div>
  `,
})
export class CreateModuleDialogComponent {
  public name = signal('');
  public selectedIcon = signal('solarFolderBold');
  public dialogData = inject(MAT_DIALOG_DATA);
  public resource = createResourceSignal<string>();

  private folderService = inject(FolderService);
  private dialogRef = inject(MatDialogRef<CreateModuleDialogComponent>);

  iconOptions: IconOption[] = [
    { label: 'Padrão', iconName: 'solarFolderBold', colorClass: 'text-gray-500' },
    { label: 'Matemática', iconName: 'solarCalculatorBold', colorClass: 'text-blue-500' },
    { label: 'Física', iconName: 'solarAtomBold', colorClass: 'text-purple-500' },
    { label: 'Programação', iconName: 'solarCodeBold', colorClass: 'text-green-500' },
    { label: 'Engenharia', iconName: 'saxCpuBold', colorClass: 'text-cyan-500' },
    { label: 'Química', iconName: 'solarTestTubeBold', colorClass: 'text-yellow-500' },
    { label: 'Biologia', iconName: 'solarLeafBold', colorClass: 'text-emerald-500' },
    { label: 'História', iconName: 'solarBook2Bold', colorClass: 'text-amber-500' },
    { label: 'Literatura', iconName: 'solarBookBookmarkBold', colorClass: 'text-pink-500' },
    { label: 'Arte', iconName: 'solarPaletteBold', colorClass: 'text-rose-500' },
    { label: 'Música', iconName: 'solarMusicLibrary2Bold', colorClass: 'text-violet-500' },
    { label: 'Negócios', iconName: 'solarCaseBold', colorClass: 'text-orange-500' },
  ];

  public selectIcon(option: IconOption) {
    this.selectedIcon.set(option.iconName);
  }

  public async create(): Promise<void> {
    if (!this.name()) {
      this.resource.setError('Selecione o nome');
      return;
    }

    const parentId = this.dialogData?.parentId;

    const data = {
      name: this.name(),
      icon: this.selectedIcon(),
      parentId: parentId,
    } satisfies CreateFolderDTO;

    this.resource.setLoading();

    try {
      await firstValueFrom(this.folderService.create(data));

      this.resource.setSuccess('Criado com sucesso!');
    } catch (err: unknown) {
      let message = 'Houve um erro desconhecido!';

      if (err instanceof HttpErrorResponse) {
        message = err.error?.message || message;
      }

      this.resource.setError(message);
    } finally {
      this.dialogRef.close({
        name: this.name(),
        icon: this.selectedIcon(),
        type: 'folder',
      });
    }
  }

  public close() {
    this.dialogRef.close();
  }
}
