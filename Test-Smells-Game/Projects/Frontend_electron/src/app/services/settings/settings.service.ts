import {Injectable} from '@angular/core';
import {ElectronService} from "ngx-electron";

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private _electronService: ElectronService) { }

  checkDependencies(){
    this._electronService.ipcRenderer.send("checkDependencies")
  }
}
