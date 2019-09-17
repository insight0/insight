import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  CertificationComponent,
  CertificationDetailComponent,
  CertificationUpdateComponent,
  CertificationDeletePopupComponent,
  CertificationDeleteDialogComponent,
  certificationRoute,
  certificationPopupRoute
} from './';

const ENTITY_STATES = [...certificationRoute, ...certificationPopupRoute];

@NgModule({
  imports: [InsightSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CertificationComponent,
    CertificationDetailComponent,
    CertificationUpdateComponent,
    CertificationDeleteDialogComponent,
    CertificationDeletePopupComponent
  ],
  entryComponents: [
    CertificationComponent,
    CertificationUpdateComponent,
    CertificationDeleteDialogComponent,
    CertificationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightCertificationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
