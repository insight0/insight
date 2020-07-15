import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { InsightSharedModule } from 'app/shared';
import { MaterialModule } from 'app/material-module';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { CvStoreComponent } from 'app/entities/cv-store/cv-store.component';
import { CvStoreUpdateComponent } from 'app/entities/cv-store/cv-store-update.component';

import { cvStoreRoute } from 'app/entities/cv-store/cv-store.route';
import { RouterModule } from '@angular/router';

const ENTITY_STATES = [...cvStoreRoute];

@NgModule({
  imports: [InsightSharedModule, MaterialModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CvStoreComponent, CvStoreUpdateComponent],
  entryComponents: [CvStoreUpdateComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightCvStoreModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
