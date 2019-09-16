import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HeaderComponent } from './header/header.component';
import { SideNavComponent } from './side-nav/side-nav.component';
import { SidePanelComponent } from './side-panel/side-panel.component';
import { Sidebar_Directives } from '../shared/directives/side-nav.directive';
import { TemplateSharedModule } from 'app/template/shared/shared.module';
import { InsightSharedModule } from 'app/shared';

@NgModule({
  imports: [TemplateSharedModule, InsightSharedModule, RouterModule],
  exports: [HeaderComponent, SideNavComponent, SidePanelComponent, Sidebar_Directives],
  declarations: [HeaderComponent, SideNavComponent, SidePanelComponent, Sidebar_Directives],
  providers: []
})
export class TemplateModule {}
