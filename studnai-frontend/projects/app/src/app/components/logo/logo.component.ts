import { Component, Input } from '@angular/core';

@Component({
  templateUrl: './logo.component.html',
  selector: 'app-logo',
})
export class LogoComponent {
  @Input({ required: false })
  public width: number = 100;
}
