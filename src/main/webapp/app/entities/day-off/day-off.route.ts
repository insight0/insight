import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DayOff } from 'app/shared/model/day-off.model';
import { DayOffService } from './day-off.service';
import { DayOffComponent } from './day-off.component';
import { DayOffDetailComponent } from './day-off-detail.component';
import { DayOffUpdateComponent } from './day-off-update.component';
import { DayOffDeletePopupComponent } from './day-off-delete-dialog.component';
import { IDayOff } from 'app/shared/model/day-off.model';

@Injectable({ providedIn: 'root' })
export class DayOffResolve implements Resolve<IDayOff> {
  constructor(private service: DayOffService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDayOff> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DayOff>) => response.ok),
        map((dayOff: HttpResponse<DayOff>) => dayOff.body)
      );
    }
    return of(new DayOff());
  }
}

export const dayOffRoute: Routes = [
  {
    path: '',
    component: DayOffComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.dayOff.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DayOffDetailComponent,
    resolve: {
      dayOff: DayOffResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.dayOff.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DayOffUpdateComponent,
    resolve: {
      dayOff: DayOffResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.dayOff.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DayOffUpdateComponent,
    resolve: {
      dayOff: DayOffResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.dayOff.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dayOffPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DayOffDeletePopupComponent,
    resolve: {
      dayOff: DayOffResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.dayOff.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
