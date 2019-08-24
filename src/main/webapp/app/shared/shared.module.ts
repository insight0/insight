import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { InsightSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [InsightSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [InsightSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightSharedModule {
  static forRoot() {
    return {
      ngModule: InsightSharedModule
    };
  }
}
