import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  HolidayComponent,
  HolidayDetailComponent,
  HolidayUpdateComponent,
  HolidayDeletePopupComponent,
  HolidayDeleteDialogComponent,
  holidayRoute,
  holidayPopupRoute
} from './';
import { MaterialModule } from 'app/material-module';

const ENTITY_STATES = [...holidayRoute, ...holidayPopupRoute];

@NgModule({
  imports: [InsightSharedModule, RouterModule.forChild(ENTITY_STATES), MaterialModule],
  declarations: [
    HolidayComponent,
    HolidayDetailComponent,
    HolidayUpdateComponent,
    HolidayDeleteDialogComponent,
    HolidayDeletePopupComponent
  ],
  entryComponents: [HolidayComponent, HolidayUpdateComponent, HolidayDeleteDialogComponent, HolidayDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightHolidayModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
