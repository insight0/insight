import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IFunctionalRole } from 'app/shared/model/functional-role.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { FunctionalRoleService } from './functional-role.service';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { IHoliday } from 'app/shared/model/holiday.model';

@Component({
  selector: 'jhi-functional-role',
  templateUrl: './functional-role.component.html',
  styleUrls: ['./functional-role.scss']
})
export class FunctionalRoleComponent implements OnInit, OnDestroy {
  functionalRoles: IFunctionalRole[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  operation: boolean;

  displayedColumns: string[] = ['name', 'description', 'maxHolidays', 'workingHours', 'operations', 'actions'];
  dataSource: MatTableDataSource<IFunctionalRole>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(
    protected functionalRoleService: FunctionalRoleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.functionalRoles = [];

    this.initSource();
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  initSource() {
    this.dataSource = new MatTableDataSource(this.functionalRoles);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadAll() {
    if (this.currentSearch) {
      this.functionalRoleService
        .search({})
        .subscribe(
          (res: HttpResponse<IFunctionalRole[]>) => this.paginateFunctionalRoles(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.functionalRoleService
      .query({})
      .subscribe(
        (res: HttpResponse<IFunctionalRole[]>) => this.paginateFunctionalRoles(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  reset() {
    this.page = 0;
    this.functionalRoles = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.functionalRoles = [];
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
    this.functionalRoles = [];
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
    this.registerChangeInFunctionalRoles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFunctionalRole) {
    return item.id;
  }

  registerChangeInFunctionalRoles() {
    this.eventSubscriber = this.eventManager.subscribe('functionalRoleListModification', response => this.reset());
  }

  protected paginateFunctionalRoles(data: IFunctionalRole[], headers: HttpHeaders) {
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.functionalRoles.push(data[i]);
    }

    this.initSource();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  filterCurrent() {
    if (this.operation) {
      this.functionalRoles = this.functionalRoles.filter((role: IFunctionalRole) => role.operations);
      this.dataSource = new MatTableDataSource(this.functionalRoles);
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
}
