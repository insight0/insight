import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  ChangeLogComponent,
  ChangeLogDetailComponent,
  ChangeLogUpdateComponent,
  ChangeLogDeletePopupComponent,
  ChangeLogDeleteDialogComponent,
  changeLogRoute,
  changeLogPopupRoute
} from './';

const ENTITY_STATES = [...changeLogRoute, ...changeLogPopupRoute];

@NgModule({
  imports: [InsightSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ChangeLogComponent,
    ChangeLogDetailComponent,
    ChangeLogUpdateComponent,
    ChangeLogDeleteDialogComponent,
    ChangeLogDeletePopupComponent
  ],
  entryComponents: [ChangeLogComponent, ChangeLogUpdateComponent, ChangeLogDeleteDialogComponent, ChangeLogDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightChangeLogModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
