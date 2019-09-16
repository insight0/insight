import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Config } from 'app/shared/model/config.model';
import { ConfigService } from './config.service';
import { ConfigComponent } from './config.component';
import { ConfigDetailComponent } from './config-detail.component';
import { ConfigUpdateComponent } from './config-update.component';
import { ConfigDeletePopupComponent } from './config-delete-dialog.component';
import { IConfig } from 'app/shared/model/config.model';

@Injectable({ providedIn: 'root' })
export class ConfigResolve implements Resolve<IConfig> {
  constructor(private service: ConfigService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConfig> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Config>) => response.ok),
        map((config: HttpResponse<Config>) => config.body)
      );
    }
    return of(new Config());
  }
}

export const configRoute: Routes = [
  {
    path: '',
    component: ConfigComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.config.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConfigDetailComponent,
    resolve: {
      config: ConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.config.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConfigUpdateComponent,
    resolve: {
      config: ConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.config.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
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

export const configPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ConfigDeletePopupComponent,
    resolve: {
      config: ConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.config.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
