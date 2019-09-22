import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  FunctionalRoleComponent,
  FunctionalRoleDetailComponent,
  FunctionalRoleUpdateComponent,
  FunctionalRoleDeletePopupComponent,
  FunctionalRoleDeleteDialogComponent,
  functionalRoleRoute,
  functionalRolePopupRoute
} from './';
import { MaterialModule } from 'app/material-module';

const ENTITY_STATES = [...functionalRoleRoute, ...functionalRolePopupRoute];

@NgModule({
  imports: [InsightSharedModule, MaterialModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FunctionalRoleComponent,
    FunctionalRoleDetailComponent,
    FunctionalRoleUpdateComponent,
    FunctionalRoleDeleteDialogComponent,
    FunctionalRoleDeletePopupComponent
  ],
  entryComponents: [
    FunctionalRoleComponent,
    FunctionalRoleUpdateComponent,
    FunctionalRoleDeleteDialogComponent,
    FunctionalRoleDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightFunctionalRoleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
