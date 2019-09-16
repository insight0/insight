import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FunctionalRole } from 'app/shared/model/functional-role.model';
import { FunctionalRoleService } from './functional-role.service';
import { FunctionalRoleComponent } from './functional-role.component';
import { FunctionalRoleDetailComponent } from './functional-role-detail.component';
import { FunctionalRoleUpdateComponent } from './functional-role-update.component';
import { FunctionalRoleDeletePopupComponent } from './functional-role-delete-dialog.component';
import { IFunctionalRole } from 'app/shared/model/functional-role.model';

@Injectable({ providedIn: 'root' })
export class FunctionalRoleResolve implements Resolve<IFunctionalRole> {
  constructor(private service: FunctionalRoleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFunctionalRole> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FunctionalRole>) => response.ok),
        map((functionalRole: HttpResponse<FunctionalRole>) => functionalRole.body)
      );
    }
    return of(new FunctionalRole());
  }
}

export const functionalRoleRoute: Routes = [
  {
    path: '',
    component: FunctionalRoleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.functionalRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FunctionalRoleDetailComponent,
    resolve: {
      functionalRole: FunctionalRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.functionalRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FunctionalRoleUpdateComponent,
    resolve: {
      functionalRole: FunctionalRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.functionalRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FunctionalRoleUpdateComponent,
    resolve: {
      functionalRole: FunctionalRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.functionalRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const functionalRolePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FunctionalRoleDeletePopupComponent,
    resolve: {
      functionalRole: FunctionalRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.functionalRole.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
