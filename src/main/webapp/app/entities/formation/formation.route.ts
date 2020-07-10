import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { FormationComponent } from 'app/entities/formation/formation.commponent';
import { FormationUpdateComponent } from 'app/entities/formation/formation-update.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { Formation, IFormation } from 'app/shared/model/formation.model';
import { FormationService } from 'app/entities/formation/formation.service';
import { FormationDetailComponent } from 'app/entities/formation/formation-detail.component';
import { FormationDeleteDialogComponent } from 'app/entities/formation/formation-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class FormationRouteResolve implements Resolve<IFormation> {
  constructor(private service: FormationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFormation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Formation>) => response.ok),
        map((formation: HttpResponse<Formation>) => formation.body)
      );
    }
    return of(new Formation());
  }
}

export const formationRoute: Routes = [
  {
    path: '',
    component: FormationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.formations.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormationUpdateComponent,
    resolve: {
      functionalRole: FormationRouteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.formations.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormationDetailComponent,
    resolve: {
      formation: FormationRouteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.formations.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormationUpdateComponent,
    resolve: {
      formation: FormationRouteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.formations.home.title'
    }
  }
];

export const formationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FormationDeleteDialogComponent,
    resolve: {
      formation: FormationRouteResolve
    },
    data: {
      authorities: ['ROLE_USER']
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
