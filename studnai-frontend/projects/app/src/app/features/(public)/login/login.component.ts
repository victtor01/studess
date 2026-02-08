import { LoaderComponent } from '@/components/loader/loader.component';
import { LogoComponent } from '@/components/logo/logo.component';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { createResourceSignal } from '@core/models/ResourceState';
import { AuthService } from '@core/services/auth.service';
import { NgIcon, provideIcons } from '@ng-icons/core';
import { saxArrowRight3Outline } from '@ng-icons/iconsax/outline';
import { firstValueFrom } from 'rxjs';

@Component({
  templateUrl: './login.component.html',
  imports: [LogoComponent, ReactiveFormsModule, LoaderComponent, NgIcon],
  viewProviders: [provideIcons({ saxArrowRight3Outline })],
  styles: `
    .banner-clip {
      clip-path: polygon(0 0, 100% 0, 85% 100%, 0 100%);
    }
  `,
})
export class LoginComponent {
  private authService = inject(AuthService);

  public emailControl = new FormControl('', {
    nonNullable: true,
    validators: [Validators.required, Validators.email],
  });

  public sended = signal<boolean>(false);

  public state = createResourceSignal<string>();

  public async submit(): Promise<void> {
    this.state.setLoading();

    if (!this.emailControl.valid) {
      this.state.setError('Email inválido');
      return;
    }

    try {
      const res = await firstValueFrom(this.authService.request(this.emailControl.value!));

      this.state.setSuccess(res?.message);
    } catch (ex: unknown) {
      if (ex instanceof HttpErrorResponse) {
        this.state.setError(ex.error?.message);
      } else {
        this.state.setError('Houve um erro interno no server');
      }
    }
  }
}
