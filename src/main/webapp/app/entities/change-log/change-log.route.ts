import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChangeLog } from 'app/shared/model/change-log.model';
import { ChangeLogService } from './change-log.service';
import { ChangeLogComponent } from './change-log.component';
import { ChangeLogDetailComponent } from './change-log-detail.component';
import { ChangeLogUpdateComponent } from './change-log-update.component';
import { ChangeLogDeletePopupComponent } from './change-log-delete-dialog.component';
import { IChangeLog } from 'app/shared/model/change-log.model';

@Injectable({ providedIn: 'root' })
export class ChangeLogResolve implements Resolve<IChangeLog> {
  constructor(private service: ChangeLogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChangeLog> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ChangeLog>) => response.ok),
        map((changeLog: HttpResponse<ChangeLog>) => changeLog.body)
      );
    }
    return of(new ChangeLog());
  }
}

export const changeLogRoute: Routes = [
  {
    path: '',
    component: ChangeLogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.changeLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChangeLogDetailComponent,
    resolve: {
      changeLog: ChangeLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.changeLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChangeLogUpdateComponent,
    resolve: {
      changeLog: ChangeLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.changeLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChangeLogUpdateComponent,
    resolve: {
      changeLog: ChangeLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.changeLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const changeLogPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ChangeLogDeletePopupComponent,
    resolve: {
      changeLog: ChangeLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.changeLog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
