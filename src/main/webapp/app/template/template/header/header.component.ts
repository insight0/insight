import { Component } from '@angular/core';
import { TemplateService } from '../../shared/services/template.service';

import { JhiTrackerService } from 'app/core';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from 'app/entities/notification';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {
  searchModel: string;
  isCollapse: boolean;
  isOpen: boolean;
  searchActived: boolean = false;
  notifications: INotification[];

  notifNbr = 0;

  constructor(
    private tplSvc: TemplateService,
    protected jhiAlertService: JhiAlertService,
    private trackerService: JhiTrackerService,
    protected notificationService: NotificationService
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
}
