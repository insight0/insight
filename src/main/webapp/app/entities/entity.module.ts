import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'day-off',
        loadChildren: () => import('./day-off/day-off.module').then(m => m.InsightDayOffModule)
      },
      {
        path: 'change-log',
        loadChildren: () => import('./change-log/change-log.module').then(m => m.InsightChangeLogModule)
      },
      {
        path: 'holiday',
        loadChildren: () => import('./holiday/holiday.module').then(m => m.InsightHolidayModule)
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.InsightContactModule)
      },
      {
        path: 'functional-role',
        loadChildren: () => import('./functional-role/functional-role.module').then(m => m.InsightFunctionalRoleModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.InsightAddressModule)
      },
      {
        path: 'salary-package',
        loadChildren: () => import('./salary-package/salary-package.module').then(m => m.InsightSalaryPackageModule)
      },
      {
        path: 'organisation',
        loadChildren: () => import('./organisation/organisation.module').then(m => m.InsightOrganisationModule)
      },
      {
        path: 'contract',
        loadChildren: () => import('./contract/contract.module').then(m => m.InsightContractModule)
      },
      {
        path: 'document-placeholder',
        loadChildren: () => import('./document-placeholder/document-placeholder.module').then(m => m.InsightDocumentPlaceholderModule)
      },
      {
        path: 'config',
        loadChildren: () => import('./config/config.module').then(m => m.InsightConfigModule)
      },
      {
        path: 'email-template',
        loadChildren: () => import('./email-template/email-template.module').then(m => m.InsightEmailTemplateModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InsightEntityModule {}
