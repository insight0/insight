import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { InsightSharedModule } from 'app/shared';
import { MaterialModule } from 'app/material-module';
import { RouterModule } from '@angular/router';

import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { FormationComponent } from 'app/entities/formation/formation.commponent';
import { formationRoute } from 'app/entities/formation/formation.route';
import { FormationUpdateComponent } from 'app/entities/formation/formation-update.component';
import { FormationDetailComponent } from 'app/entities/formation/formation-detail.component';
import { FormationDeleteDialogComponent, FormationDeletePopup } from 'app/entities/formation/formation-delete-dialog.component';

const ENTITY_STATES = [...formationRoute];

@NgModule({
  imports: [InsightSharedModule, MaterialModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FormationComponent,
    FormationUpdateComponent,
    FormationDetailComponent,
    FormationDeleteDialogComponent,
    FormationDeletePopup
  ],
  entryComponents: [FormationDeleteDialogComponent, FormationDeletePopup, FormationDeleteDialogComponent, FormationUpdateComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightFormationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
