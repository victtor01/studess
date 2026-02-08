import { HomeHeaderComponent } from '@/components/home/home-header/home-header.component';
import { SidebarComponent } from '@/components/sidebar/sidebar.component';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  template: `
    <div class="flex w-full h-dvh overflow-hidden bg-surface-50">
      <app-sidebar />
      <section class="flex flex-col w-full">
        <app-header />
        <router-outlet />
      </section>
    </div>
  `,
  imports: [SidebarComponent, HomeHeaderComponent, RouterModule],
})
export class HomeLayoutComponent {}
