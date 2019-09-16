import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDayOff } from 'app/shared/model/day-off.model';
import { DayOffService } from './day-off.service';

@Component({
  selector: 'jhi-day-off-delete-dialog',
  templateUrl: './day-off-delete-dialog.component.html'
})
export class DayOffDeleteDialogComponent {
  dayOff: IDayOff;

  constructor(protected dayOffService: DayOffService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.dayOffService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dayOffListModification',
        content: 'Deleted an dayOff'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-day-off-delete-popup',
  template: ''
})
export class DayOffDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dayOff }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DayOffDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dayOff = dayOff;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/day-off', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/day-off', { outlets: { popup: null } }]);
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
