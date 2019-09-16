import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  SalaryPackageComponent,
  SalaryPackageDetailComponent,
  SalaryPackageUpdateComponent,
  SalaryPackageDeletePopupComponent,
  SalaryPackageDeleteDialogComponent,
  salaryPackageRoute,
  salaryPackagePopupRoute
} from './';

const ENTITY_STATES = [...salaryPackageRoute, ...salaryPackagePopupRoute];

@NgModule({
  imports: [InsightSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SalaryPackageComponent,
    SalaryPackageDetailComponent,
    SalaryPackageUpdateComponent,
    SalaryPackageDeleteDialogComponent,
    SalaryPackageDeletePopupComponent
  ],
  entryComponents: [
    SalaryPackageComponent,
    SalaryPackageUpdateComponent,
    SalaryPackageDeleteDialogComponent,
    SalaryPackageDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightSalaryPackageModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
