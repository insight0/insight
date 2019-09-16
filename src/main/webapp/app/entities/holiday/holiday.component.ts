import { OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IHoliday } from 'app/shared/model/holiday.model';
import { AccountService } from 'app/core';

import { HolidayService } from './holiday.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'jhi-holiday',
  templateUrl: './holiday.component.html',
  styleUrls: ['./holiday.component.scss']
})
export class HolidayComponent implements OnInit, OnDestroy {
  holidays: IHoliday[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  currentYear: boolean;

  displayedColumns: string[] = ['name', 'date', 'duration', 'paid', 'options'];
  dataSource: MatTableDataSource<IHoliday>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(
    protected holidayService: HolidayService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.holidays = [];
    this.dataSource = new MatTableDataSource(this.holidays);
  }

  initSource() {
    this.dataSource = new MatTableDataSource(this.holidays);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadAll() {
    if (this.currentSearch) {
      this.holidayService.search({}).subscribe(
        (res: HttpResponse<IHoliday[]>) => {
          this.holidays = res.body;
          this.initSource();
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      return;
    }
    this.holidayService.query({}).subscribe(
      (res: HttpResponse<IHoliday[]>) => {
        this.holidays = res.body;
        this.initSource();
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  filterCurrent() {
    if (this.currentYear) {
      this.holidays = this.holidays.filter(holiday => holiday.year === new Date().getFullYear());
      this.dataSource = new MatTableDataSource(this.holidays);
    } else {
      this.loadAll();
    }
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHolidays();
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  registerChangeInHolidays() {
    this.eventSubscriber = this.eventManager.subscribe('holidayListModification', response => this.reset());
  }

  reset() {
    this.holidays = [];
    this.loadAll();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
