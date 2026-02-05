import { inject, Injectable } from '@angular/core';
import { ApiService } from '@core/http/api.service';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private api = inject(ApiService);

  public request(email: string) {
    return this.api.post<{ message: string }>('/auth/request', { email });
  }

  public verify(token: string) {
    return this.api.get('/auth/callback', { params: { token } });
  }
}
