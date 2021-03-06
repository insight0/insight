import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { InsightSharedModule } from 'app/shared';
import { AccountService, InsightCoreModule } from 'app/core';
import { InsightAppRoutingModule } from './app-routing.module';
import { InsightHomeModule } from './home/home.module';
import { InsightAccountModule } from './account/account.module';
import { InsightEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
import { MaterialModule } from 'app/material-module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TemplateSharedModule } from 'app/template/shared/shared.module';
import { AuthenticationLayoutComponent } from 'app/template/common/authentication-layout.component';
import { TemplateModule } from 'app/template/template/template.module';
import * as $ from 'jquery';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    TemplateSharedModule,
    TemplateModule,
    MaterialModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-' }),
    NgJhipsterModule.forRoot({
      // set below to true to make alerts look like toast
      alertAsToast: false,
      alertTimeout: 5000,
      i18nEnabled: true,
      defaultI18nLang: 'en'
    }),
    InsightSharedModule.forRoot(),
    InsightCoreModule,
    InsightHomeModule,
    InsightAccountModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    InsightEntityModule,
    InsightAppRoutingModule
  ],
  declarations: [
    JhiMainComponent,
    AuthenticationLayoutComponent,
    AuthenticationLayoutComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
    }
  ],
  bootstrap: [JhiMainComponent]
})
export class InsightAppModule {
  constructor(private dpConfig: NgbDatepickerConfig, private accountService: AccountService) {
    console.log('MODULE');
    console.log('MODULE');
    console.log('MODULE');
    console.log('MODULE');
    console.log('MODULE');
    console.log('MODULE');
    console.log(this.accountService);
    console.log(this.accountService);
    console.log(this.accountService);
    console.log(this.accountService);
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}
