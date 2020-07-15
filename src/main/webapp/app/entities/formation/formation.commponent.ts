import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormationService } from 'app/entities/formation/formation.service';
import { JhiAlertService } from 'ng-jhipster';
import { MatTableDataSource } from '@angular/material/table';
import { IFormation } from 'app/shared/model/formation.model';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { FormationDeleteDialogComponent } from 'app/entities/formation/formation-delete-dialog.component';

@Component({
  selector: 'jhi-formation',
  templateUrl: 'formation.component.html',
  styleUrls: ['formation.scss']
})
export class FormationComponent implements OnInit, OnDestroy {
  formations: IFormation[] = new Array();
  dataSource: MatTableDataSource<IFormation>;
  displayedColumns: string[] = ['title', 'society', 'location', 'concerned', 'actions'];
  currentSearch: string;
  totalItems: number;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(
    protected formationService: FormationService,
    protected alertJhiService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected dialog: MatDialog
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  ngOnDestroy(): void {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    if (this.currentSearch) {
      this.formationService
        .search({})
        .subscribe(
          (res: HttpResponse<IFormation[]>) => this.paginateFormations(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.formationService
      .query({})
      .subscribe(
        (res: HttpResponse<IFormation[]>) => this.paginateFormations(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  initSource() {
    this.dataSource = new MatTableDataSource(this.formations);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  protected paginateFormations(data: IFormation[], headers: HttpHeaders) {
    this.totalItems = parseInt(headers.get('X-Total-count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.formations.push(data[i]);
    }
    this.initSource();
  }

  protected onError(errorMessage: string) {
    this.alertJhiService.error(errorMessage, null, null);
  }

  delete(formationId: string) {
    this.formationService.delete(formationId);
  }

  confirmDialog(key: string): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '40%';
    dialogConfig.height = '25%';
    dialogConfig.data = key;
    this.dialog.open(FormationDeleteDialogComponent, {
      width: '390px',
      disableClose: true
    });
  }
}
