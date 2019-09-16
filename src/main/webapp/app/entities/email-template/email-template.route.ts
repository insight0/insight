import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmailTemplate } from 'app/shared/model/email-template.model';
import { EmailTemplateService } from './email-template.service';
import { EmailTemplateUpdateComponent } from './email-template-update.component';
import { IEmailTemplate } from 'app/shared/model/email-template.model';

@Injectable({ providedIn: 'root' })
export class EmailTemplateResolve implements Resolve<IEmailTemplate> {
  constructor(private service: EmailTemplateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmailTemplate> {
    return this.service.find().pipe(
      filter((response: HttpResponse<EmailTemplate>) => response.ok),
      map((emailTemplate: HttpResponse<EmailTemplate>) => emailTemplate.body)
    );
  }
}

export const emailTemplateRoute: Routes = [
  {
    path: 'view',
    component: EmailTemplateUpdateComponent,
    resolve: {
      emailTemplate: EmailTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.emailTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const emailTemplatePopupRoute: Routes = [];
