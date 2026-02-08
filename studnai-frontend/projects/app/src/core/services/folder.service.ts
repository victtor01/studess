import { inject, Injectable } from '@angular/core';
import { CreateFolderDTO } from '@core/dtos/request/create-folder.dto';
import { ApiService } from '@core/http/api.service';
import { Folder } from '@core/models/Folder';

@Injectable({ providedIn: 'root' })
export class FolderService {
  private api = inject(ApiService);

  public create(data: CreateFolderDTO) {
    return this.api.post<Folder, CreateFolderDTO>('/folders', data);
  }
}
