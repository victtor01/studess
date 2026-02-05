// src/app/core/http/api.service.ts
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export abstract class ApiService {
  public readonly baseUrl = environment.apiUrl;
  public readonly http = inject(HttpClient);

  public get<T>(
    url: string,
    options?: {
      params?: Record<string, string | number | boolean>;
      headers?: HttpHeaders;
    },
  ): Observable<T> {
    return this.http.get<T>(this.buildUrl(url), {
      params: this.buildParams(options?.params),
      headers: options?.headers,
    });
  }

  public post<T, B = unknown>(
    url: string,
    body: B,
    options?: {
      params?: Record<string, string | number | boolean>;
      headers?: HttpHeaders;
    },
  ): Observable<T> {
    return this.http.post<T>(this.buildUrl(url), body, {
      params: this.buildParams(options?.params),
      headers: options?.headers,
    });
  }

  public put<T, B = unknown>(
    url: string,
    body: B,
    options?: {
      params?: Record<string, string | number | boolean>;
      headers?: HttpHeaders;
    },
  ): Observable<T> {
    return this.http.put<T>(this.buildUrl(url), body, {
      params: this.buildParams(options?.params),
      headers: options?.headers,
    });
  }

  public delete<T>(
    url: string,
    options?: {
      params?: Record<string, string | number | boolean>;
      headers?: HttpHeaders;
    },
  ): Observable<T> {
    return this.http.delete<T>(this.buildUrl(url), {
      params: this.buildParams(options?.params),
      headers: options?.headers,
    });
  }

  // =========================
  // helpers
  // =========================

  private buildUrl(path: string): string {
    return `${this.baseUrl}${path}`;
  }

  private buildParams(params?: Record<string, string | number | boolean>): HttpParams | undefined {
    if (!params) return undefined;

    let httpParams = new HttpParams();

    Object.entries(params).forEach(([key, value]) => {
      if (value !== null && value !== undefined) {
        httpParams = httpParams.set(key, String(value));
      }
    });

    return httpParams;
  }
}
