import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChangeLog } from 'app/shared/model/change-log.model';
import { ChangeLogService } from './change-log.service';

@Component({
  selector: 'jhi-change-log-delete-dialog',
  templateUrl: './change-log-delete-dialog.component.html'
})
export class ChangeLogDeleteDialogComponent {
  changeLog: IChangeLog;

  constructor(protected changeLogService: ChangeLogService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.changeLogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'changeLogListModification',
        content: 'Deleted an changeLog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-change-log-delete-popup',
  template: ''
})
export class ChangeLogDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ changeLog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ChangeLogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.changeLog = changeLog;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/change-log', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/change-log', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
