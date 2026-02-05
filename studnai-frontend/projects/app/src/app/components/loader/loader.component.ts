import { Component, input } from '@angular/core';

@Component({
  selector: 'app-loader-classic',
  standalone: true,
  template: ` <div class="loader" [style.--loader-color]="color()"></div>`,
  styles: `
    .loader {
      /* Valor padrão caso nenhuma cor seja passada */
      --loader-color: #000;

      width: 100%; /* Adicionei um tamanho base */
      aspect-ratio: 1;
      display: grid;
      border-radius: 50%;
      background:
        linear-gradient(
            0deg,
            color-mix(in srgb, var(--loader-color), transparent 50%) 30%,
            #0000 0 70%,
            var(--loader-color) 0
          )
          50%/8% 100%,
        linear-gradient(
            90deg,
            color-mix(in srgb, var(--loader-color), transparent 75%) 30%,
            #0000 0 70%,
            color-mix(in srgb, var(--loader-color), transparent 25%) 0
          )
          50%/100% 8%;
      background-repeat: no-repeat;
      animation: l23 1s infinite steps(12);
    }
    .loader::before,
    .loader::after {
      content: '';
      grid-area: 1/1;
      border-radius: 50%;
      background: inherit;
      opacity: 0.915;
      transform: rotate(30deg);
    }
    .loader::after {
      opacity: 0.83;
      transform: rotate(60deg);
    }
    @keyframes l23 {
      100% {
        transform: rotate(1turn);
      }
    }
  `,
})
export class Loader {
  // Signal Input (Angular 19)
  color = input<string>('currentColor');
}
