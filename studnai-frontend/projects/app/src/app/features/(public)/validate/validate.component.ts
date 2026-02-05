// validate.component.ts
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { createResourceSignal } from '@core/models/ResourceState';
import { AuthService } from '@core/services/auth.service';
import { firstValueFrom } from 'rxjs';

@Component({
  templateUrl: './validate.component.html',
  styles: [
    `
      @keyframes fadeIn {
        from {
          opacity: 0;
        }
        to {
          opacity: 1;
        }
      }

      @keyframes spin {
        to {
          transform: rotate(360deg);
        }
      }

      .animate-fade {
        animation: fadeIn 0.3s ease-out;
      }

      .spinner {
        border: 2px solid #e5e7eb;
        border-top-color: #18181b;
        border-radius: 50%;
        width: 32px;
        height: 32px;
        animation: spin 0.6s linear infinite;
      }
    `,
  ],
})
export class ValidateComponent {
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  public state = createResourceSignal<string>();
  private currentToken: string | null = null;

  constructor() {
    const token = this.route.snapshot.queryParamMap.get('token');

    if (!token) {
      this.state.setError('Token não encontrado na URL');
      return;
    }

    this.currentToken = token;
    this.validateToken(token);
  }

  private async validateToken(token: string): Promise<void> {
    this.state.setLoading();

    try {
      const res = await firstValueFrom(this.authService.verify(token));
      this.state.setSuccess('Validado com sucesso!');
    } catch (ex: unknown) {
      if (ex instanceof HttpErrorResponse) {
        this.state.setError(ex.error?.message || 'Token inválido ou expirado');
      } else {
        this.state.setError('Erro ao validar token');
      }
    }
  }

  navigateToDashboard(): void {
    this.router.navigate(['/dashboard']);
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }

  retryValidation(): void {
    if (this.currentToken) {
      this.validateToken(this.currentToken);
    }
  }
}
