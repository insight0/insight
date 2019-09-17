import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICertification } from 'app/shared/model/certification.model';
import { AccountService } from 'app/core';
import { CertificationService } from './certification.service';

@Component({
  selector: 'jhi-certification',
  templateUrl: './certification.component.html'
})
export class CertificationComponent implements OnInit, OnDestroy {
  certifications: ICertification[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected certificationService: CertificationService,
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
      this.certificationService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICertification[]>) => res.ok),
          map((res: HttpResponse<ICertification[]>) => res.body)
        )
        .subscribe((res: ICertification[]) => (this.certifications = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.certificationService
      .query()
      .pipe(
        filter((res: HttpResponse<ICertification[]>) => res.ok),
        map((res: HttpResponse<ICertification[]>) => res.body)
      )
      .subscribe(
        (res: ICertification[]) => {
          this.certifications = res;
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
    this.registerChangeInCertifications();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICertification) {
    return item.id;
  }

  registerChangeInCertifications() {
    this.eventSubscriber = this.eventManager.subscribe('certificationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
