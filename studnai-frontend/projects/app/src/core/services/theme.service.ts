import { effect, Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  // Signal inicializado buscando o valor do cookie ou preferência do sistema
  public theme = signal<'light' | 'dark'>(this.getThemeFromCookies());

  constructor() {
    effect(() => {
      const currentTheme = this.theme();
      const root = window.document.documentElement;

      if (currentTheme === 'dark') {
        root.classList.add('dark');
      } else {
        root.classList.remove('dark');
      }

      this.setCookie('theme', currentTheme, 365);
    });
  }

  private getThemeFromCookies(): 'light' | 'dark' {
    if (typeof document === 'undefined') return 'light'; // Fallback para SSR

    const name = 'theme=';
    const decodedCookie = decodeURIComponent(document.cookie);
    const ca = decodedCookie.split(';');

    for (let i = 0; i < ca.length; i++) {
      let c = ca[i].trim();
      if (c.indexOf(name) === 0) {
        return c.substring(name.length, c.length) as 'light' | 'dark';
      }
    }

    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }

  private setCookie(name: string, value: string, days: number) {
    const date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    const expires = 'expires=' + date.toUTCString();
    document.cookie = `${name}=${value};${expires};path=/;SameSite=Lax`;
  }

  toggleTheme() {
    this.theme.update((current) => (current === 'light' ? 'dark' : 'light'));
  }

  setDarkMode() {
    this.theme.set('dark');
  }

  setLightMode() {
    this.theme.set('light');
  }
}
