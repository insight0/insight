import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Certification } from 'app/shared/model/certification.model';
import { CertificationService } from './certification.service';
import { CertificationComponent } from './certification.component';
import { CertificationDetailComponent } from './certification-detail.component';
import { CertificationUpdateComponent } from './certification-update.component';
import { CertificationDeletePopupComponent } from './certification-delete-dialog.component';
import { ICertification } from 'app/shared/model/certification.model';

@Injectable({ providedIn: 'root' })
export class CertificationResolve implements Resolve<ICertification> {
  constructor(private service: CertificationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICertification> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Certification>) => response.ok),
        map((certification: HttpResponse<Certification>) => certification.body)
      );
    }
    return of(new Certification());
  }
}

export const certificationRoute: Routes = [
  {
    path: '',
    component: CertificationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.certification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CertificationDetailComponent,
    resolve: {
      certification: CertificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.certification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CertificationUpdateComponent,
    resolve: {
      certification: CertificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.certification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CertificationUpdateComponent,
    resolve: {
      certification: CertificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.certification.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const certificationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CertificationDeletePopupComponent,
    resolve: {
      certification: CertificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.certification.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
