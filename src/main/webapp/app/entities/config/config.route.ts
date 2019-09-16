import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Config } from 'app/shared/model/config.model';
import { ConfigService } from './config.service';
import { ConfigUpdateComponent } from './config-update.component';
import { IConfig } from 'app/shared/model/config.model';

@Injectable({ providedIn: 'root' })
export class ConfigResolve implements Resolve<IConfig> {
  constructor(private service: ConfigService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConfig> {
    return this.service.find().pipe(
      filter((response: HttpResponse<Config>) => response.ok),
      map((config: HttpResponse<Config>) => config.body)
    );
  }
}

export const configRoute: Routes = [
  {
    path: 'view',
    component: ConfigUpdateComponent,
    resolve: {
      config: ConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.config.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const configPopupRoute: Routes = [];
