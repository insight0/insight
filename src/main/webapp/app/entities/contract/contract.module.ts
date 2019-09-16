import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { InsightSharedModule } from 'app/shared';
import {
  ContractComponent,
  ContractDetailComponent,
  ContractUpdateComponent,
  ContractDeletePopupComponent,
  ContractDeleteDialogComponent,
  contractRoute,
  contractPopupRoute
} from './';

const ENTITY_STATES = [...contractRoute, ...contractPopupRoute];

@NgModule({
  imports: [InsightSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContractComponent,
    ContractDetailComponent,
    ContractUpdateComponent,
    ContractDeleteDialogComponent,
    ContractDeletePopupComponent
  ],
  entryComponents: [ContractComponent, ContractUpdateComponent, ContractDeleteDialogComponent, ContractDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightContractModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
