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
        path: 'formations',
        loadChildren: () => import('./formation/formation.module').then(m => m.InsightFormationModule)
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
      },
      {
        path: 'notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.InsightNotificationModule)
      },
      {
        path: 'degree',
        loadChildren: () => import('./degree/degree.module').then(m => m.InsightDegreeModule)
      },
      {
        path: 'certification',
        loadChildren: () => import('./certification/certification.module').then(m => m.InsightCertificationModule)
      },
      {
        path: 'skill',
        loadChildren: () => import('./skill/skill.module').then(m => m.InsightSkillModule)
      },
      {
        path: 'cv-store',
        loadChildren: () => import('./cv-store/cv-store.module').then(m => m.InsightCvStoreModule)
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
