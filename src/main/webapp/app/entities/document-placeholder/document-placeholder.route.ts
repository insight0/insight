import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DocumentPlaceholder } from 'app/shared/model/document-placeholder.model';
import { DocumentPlaceholderService } from './document-placeholder.service';
import { DocumentPlaceholderComponent } from './document-placeholder.component';
import { DocumentPlaceholderDetailComponent } from './document-placeholder-detail.component';
import { DocumentPlaceholderUpdateComponent } from './document-placeholder-update.component';
import { DocumentPlaceholderDeletePopupComponent } from './document-placeholder-delete-dialog.component';
import { IDocumentPlaceholder } from 'app/shared/model/document-placeholder.model';

@Injectable({ providedIn: 'root' })
export class DocumentPlaceholderResolve implements Resolve<IDocumentPlaceholder> {
  constructor(private service: DocumentPlaceholderService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDocumentPlaceholder> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DocumentPlaceholder>) => response.ok),
        map((documentPlaceholder: HttpResponse<DocumentPlaceholder>) => documentPlaceholder.body)
      );
    }
    return of(new DocumentPlaceholder());
  }
}

export const documentPlaceholderRoute: Routes = [
  {
    path: '',
    component: DocumentPlaceholderComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.documentPlaceholder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DocumentPlaceholderDetailComponent,
    resolve: {
      documentPlaceholder: DocumentPlaceholderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.documentPlaceholder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DocumentPlaceholderUpdateComponent,
    resolve: {
      documentPlaceholder: DocumentPlaceholderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.documentPlaceholder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DocumentPlaceholderUpdateComponent,
    resolve: {
      documentPlaceholder: DocumentPlaceholderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.documentPlaceholder.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const documentPlaceholderPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DocumentPlaceholderDeletePopupComponent,
    resolve: {
      documentPlaceholder: DocumentPlaceholderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'insightApp.documentPlaceholder.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
