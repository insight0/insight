import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  OrganisationComponent,
  OrganisationDetailComponent,
  OrganisationUpdateComponent,
  OrganisationDeletePopupComponent,
  OrganisationDeleteDialogComponent,
  organisationRoute,
  organisationPopupRoute
} from './';

const ENTITY_STATES = [...organisationRoute, ...organisationPopupRoute];

@NgModule({
  imports: [InsightSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrganisationComponent,
    OrganisationDetailComponent,
    OrganisationUpdateComponent,
    OrganisationDeleteDialogComponent,
    OrganisationDeletePopupComponent
  ],
  entryComponents: [
    OrganisationComponent,
    OrganisationUpdateComponent,
    OrganisationDeleteDialogComponent,
    OrganisationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightOrganisationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
