import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { DayOffStatus, IDayOff } from 'app/shared/model/day-off.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { DayOffService } from './day-off.service';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'jhi-day-off',
  templateUrl: './day-off.component.html',
  styleUrls: ['./dayoff.component.scss']
})
export class DayOffComponent implements OnInit, OnDestroy {
  dayOffs: IDayOff[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  notClosed: boolean;

  displayedColumns: string[] = ['picture', 'startDate', 'endDate', 'duration', 'status', 'options'];
  dataSource: MatTableDataSource<IDayOff>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(
    protected dayOffService: DayOffService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.dayOffs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  initSource() {
    this.dataSource = new MatTableDataSource(this.dayOffs);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadAll() {
    if (this.currentSearch) {
      this.dayOffService
        .search({})
        .subscribe(
          (res: HttpResponse<IDayOff[]>) => this.paginateDayOffs(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.dayOffService
      .query({})
      .subscribe(
        (res: HttpResponse<IDayOff[]>) => this.paginateDayOffs(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  reset() {
    this.page = 0;
    this.dayOffs = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.dayOffs = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch = '';
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.dayOffs = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = '_score';
    this.reverse = false;
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDayOffs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDayOff) {
    return item.id;
  }

  registerChangeInDayOffs() {
    this.eventSubscriber = this.eventManager.subscribe('dayOffListModification', response => this.reset());
  }

  filterCurrent() {
    if (this.notClosed) {
      this.dayOffs = this.dayOffs.filter(holiday => holiday.status === DayOffStatus.NEW);
      this.dataSource = new MatTableDataSource(this.dayOffs);
    } else {
      this.loadAll();
    }
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  protected paginateDayOffs(data: IDayOff[], headers: HttpHeaders) {
    this.dayOffs = [];

    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.dayOffs.push(data[i]);
    }

    this.initSource();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
