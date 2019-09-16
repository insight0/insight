import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  DocumentPlaceholderComponent,
  DocumentPlaceholderDetailComponent,
  DocumentPlaceholderUpdateComponent,
  DocumentPlaceholderDeletePopupComponent,
  DocumentPlaceholderDeleteDialogComponent,
  documentPlaceholderRoute,
  documentPlaceholderPopupRoute
} from './';

const ENTITY_STATES = [...documentPlaceholderRoute, ...documentPlaceholderPopupRoute];

@NgModule({
  imports: [InsightSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DocumentPlaceholderComponent,
    DocumentPlaceholderDetailComponent,
    DocumentPlaceholderUpdateComponent,
    DocumentPlaceholderDeleteDialogComponent,
    DocumentPlaceholderDeletePopupComponent
  ],
  entryComponents: [
    DocumentPlaceholderComponent,
    DocumentPlaceholderUpdateComponent,
    DocumentPlaceholderDeleteDialogComponent,
    DocumentPlaceholderDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightDocumentPlaceholderModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
