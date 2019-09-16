import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SalaryPackage } from 'app/shared/model/salary-package.model';
import { SalaryPackageService } from './salary-package.service';
import { SalaryPackageComponent } from './salary-package.component';
import { SalaryPackageDetailComponent } from './salary-package-detail.component';
import { SalaryPackageUpdateComponent } from './salary-package-update.component';
import { SalaryPackageDeletePopupComponent } from './salary-package-delete-dialog.component';
import { ISalaryPackage } from 'app/shared/model/salary-package.model';

@Injectable({ providedIn: 'root' })
export class SalaryPackageResolve implements Resolve<ISalaryPackage> {
  constructor(private service: SalaryPackageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalaryPackage> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SalaryPackage>) => response.ok),
        map((salaryPackage: HttpResponse<SalaryPackage>) => salaryPackage.body)
      );
    }
    return of(new SalaryPackage());
  }
}

export const salaryPackageRoute: Routes = [
  {
    path: '',
    component: SalaryPackageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.salaryPackage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SalaryPackageDetailComponent,
    resolve: {
      salaryPackage: SalaryPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.salaryPackage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SalaryPackageUpdateComponent,
    resolve: {
      salaryPackage: SalaryPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.salaryPackage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SalaryPackageUpdateComponent,
    resolve: {
      salaryPackage: SalaryPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.salaryPackage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const salaryPackagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SalaryPackageDeletePopupComponent,
    resolve: {
      salaryPackage: SalaryPackageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.salaryPackage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
