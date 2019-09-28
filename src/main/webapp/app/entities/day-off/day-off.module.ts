import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  DayOffComponent,
  DayOffDetailComponent,
  DayOffUpdateComponent,
  DayOffDeletePopupComponent,
  DayOffDeleteDialogComponent,
  dayOffRoute,
  dayOffPopupRoute
} from './';
import { MaterialModule } from 'app/material-module';

const ENTITY_STATES = [...dayOffRoute, ...dayOffPopupRoute];

@NgModule({
  imports: [InsightSharedModule, MaterialModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DayOffComponent, DayOffDetailComponent, DayOffUpdateComponent, DayOffDeleteDialogComponent, DayOffDeletePopupComponent],
  entryComponents: [DayOffComponent, DayOffUpdateComponent, DayOffDeleteDialogComponent, DayOffDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightDayOffModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
