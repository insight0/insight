import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { InsightSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';

@NgModule({
  imports: [InsightSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [InsightSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, PerfectScrollbarModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightSharedModule {
  static forRoot() {
    return {
      ngModule: InsightSharedModule
    };
  }
}
