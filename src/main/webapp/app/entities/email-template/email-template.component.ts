import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmailTemplate } from 'app/shared/model/email-template.model';
import { AccountService } from 'app/core';
import { EmailTemplateService } from './email-template.service';

@Component({
  selector: 'jhi-email-template',
  templateUrl: './email-template.component.html'
})
export class EmailTemplateComponent implements OnInit, OnDestroy {
  emailTemplates: IEmailTemplate[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected emailTemplateService: EmailTemplateService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.emailTemplateService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IEmailTemplate[]>) => res.ok),
          map((res: HttpResponse<IEmailTemplate[]>) => res.body)
        )
        .subscribe((res: IEmailTemplate[]) => (this.emailTemplates = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.emailTemplateService
      .query()
      .pipe(
        filter((res: HttpResponse<IEmailTemplate[]>) => res.ok),
        map((res: HttpResponse<IEmailTemplate[]>) => res.body)
      )
      .subscribe(
        (res: IEmailTemplate[]) => {
          this.emailTemplates = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEmailTemplates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEmailTemplate) {
    return item.id;
  }

  registerChangeInEmailTemplates() {
    this.eventSubscriber = this.eventManager.subscribe('emailTemplateListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
