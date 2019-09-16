import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';

import { JhiLanguageHelper } from 'app/core';
import { TemplateService } from 'app/template/shared/services/template.service';

export type HeaderType = 'header-default' | 'header-primary' | 'header-info' | 'header-success' | 'header-danger' | 'header-dark';
export type SideNavType = 'sidenav-default' | 'side-nav-dark';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
  headerSelected: HeaderType = 'header-default';
  sidenavSelected: SideNavType = 'sidenav-default';
  isCollapse: boolean;
  rtlActived: boolean = false;

  themeConfigOpen: boolean = false;

  constructor(private jhiLanguageHelper: JhiLanguageHelper, private router: Router, private tplSvc: TemplateService) {}

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'insightApp';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  ngOnInit() {
    this.tplSvc.isSideNavCollapseChanges.subscribe(isCollapse => (this.isCollapse = isCollapse));
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });
  }
}
