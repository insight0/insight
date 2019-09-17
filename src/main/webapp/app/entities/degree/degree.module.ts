import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  DegreeComponent,
  DegreeDetailComponent,
  DegreeUpdateComponent,
  DegreeDeletePopupComponent,
  DegreeDeleteDialogComponent,
  degreeRoute,
  degreePopupRoute
} from './';

const ENTITY_STATES = [...degreeRoute, ...degreePopupRoute];

@NgModule({
  imports: [InsightSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DegreeComponent, DegreeDetailComponent, DegreeUpdateComponent, DegreeDeleteDialogComponent, DegreeDeletePopupComponent],
  entryComponents: [DegreeComponent, DegreeUpdateComponent, DegreeDeleteDialogComponent, DegreeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightDegreeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
