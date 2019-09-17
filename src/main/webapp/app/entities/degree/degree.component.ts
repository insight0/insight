import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDegree } from 'app/shared/model/degree.model';
import { AccountService } from 'app/core';
import { DegreeService } from './degree.service';

@Component({
  selector: 'jhi-degree',
  templateUrl: './degree.component.html'
})
export class DegreeComponent implements OnInit, OnDestroy {
  degrees: IDegree[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected degreeService: DegreeService,
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
      this.degreeService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IDegree[]>) => res.ok),
          map((res: HttpResponse<IDegree[]>) => res.body)
        )
        .subscribe((res: IDegree[]) => (this.degrees = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.degreeService
      .query()
      .pipe(
        filter((res: HttpResponse<IDegree[]>) => res.ok),
        map((res: HttpResponse<IDegree[]>) => res.body)
      )
      .subscribe(
        (res: IDegree[]) => {
          this.degrees = res;
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
    this.registerChangeInDegrees();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDegree) {
    return item.id;
  }

  registerChangeInDegrees() {
    this.eventSubscriber = this.eventManager.subscribe('degreeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
