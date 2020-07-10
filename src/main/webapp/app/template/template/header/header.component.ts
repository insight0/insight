import { Component } from '@angular/core';
import { TemplateService } from '../../shared/services/template.service';

import { AccountService, JhiLanguageHelper, JhiTrackerService, LoginModalService, LoginService } from 'app/core';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from 'app/entities/notification';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';
import { ProfileService } from 'app/layouts';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.scss']
})
export class HeaderComponent {
  searchModel: string;
  isCollapse: boolean;
  isOpen: boolean;
  searchActived: boolean = false;
  notifications: INotification[];

  inProduction: boolean;
  isNavbarCollapsed: boolean;
  languages: any[];
  swaggerEnabled: boolean;
  modalRef: NgbModalRef;
  version: string;

  notifNbr = 0;

  constructor(
    private tplSvc: TemplateService,
    protected jhiAlertService: JhiAlertService,
    private trackerService: JhiTrackerService,
    protected notificationService: NotificationService,
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private languageHelper: JhiLanguageHelper,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router
  ) {}

  loadAll() {
    this.notificationService
      .latest()
      .pipe(
        filter((res: HttpResponse<INotification[]>) => res.ok),
        map((res: HttpResponse<INotification[]>) => res.body)
      )
      .subscribe(
        (res: INotification[]) => {
          this.notifications = res;
          for (let notif of this.notifications) {
            if (!notif.seen) {
              this.notifNbr++;
            }
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  showActivity(notif: INotification) {
    this.playAudio();
    this.notifNbr++;
    this.notifications.unshift(notif);
  }

  ngOnInit() {
    this.loadAll();
    this.trackerService.subscribeToUserNotification();
    this.trackerService.receiveUserNotification().subscribe((notif: INotification) => {
      this.showActivity(notif);
    });

    this.tplSvc.isSideNavCollapseChanges.subscribe(isCollapse => (this.isCollapse = isCollapse));
    this.tplSvc.isSidePanelOpenChanges.subscribe(isOpen => (this.isOpen = isOpen));

    this.profileService.getProfileInfo().then(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }

  toggleSideNavCollapse() {
    this.isCollapse = !this.isCollapse;
    this.tplSvc.toggleSideNavCollapse(this.isCollapse);
  }

  toggleSidePanelOpen() {
    this.isOpen = !this.isOpen;
    this.tplSvc.toggleSidePanelOpen(this.isOpen);
  }

  toggleSearch() {
    this.searchActived = !this.searchActived;
    console.log(this.searchActived);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  updateNotifView() {
    if (this.notifications) {
      this.notificationService.markAsSeen(this.notifications);
    }

    setTimeout(() => {
      this.notifNbr = 0;
    }, 2000);

    setTimeout(() => {
      if (this.notifications) {
        for (let notif of this.notifications) {
          notif.seen = true;
        }
      }
    }, 5000);
  }

  playAudio() {
    let audio = new Audio();
    audio.src = '../../../../content/sound/open-ended.mp3';
    audio.load();
    audio.play();
  }

  changeLanguage(languageKey: string) {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  logout() {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl() {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
  }
}
