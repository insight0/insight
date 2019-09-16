import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  EmailTemplateComponent,
  EmailTemplateDetailComponent,
  EmailTemplateUpdateComponent,
  EmailTemplateDeletePopupComponent,
  EmailTemplateDeleteDialogComponent,
  emailTemplateRoute,
  emailTemplatePopupRoute
} from './';

const ENTITY_STATES = [...emailTemplateRoute, ...emailTemplatePopupRoute];

@NgModule({
  imports: [InsightSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EmailTemplateComponent,
    EmailTemplateDetailComponent,
    EmailTemplateUpdateComponent,
    EmailTemplateDeleteDialogComponent,
    EmailTemplateDeletePopupComponent
  ],
  entryComponents: [
    EmailTemplateComponent,
    EmailTemplateUpdateComponent,
    EmailTemplateDeleteDialogComponent,
    EmailTemplateDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightEmailTemplateModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
