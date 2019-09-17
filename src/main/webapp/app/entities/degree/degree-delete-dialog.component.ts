import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDegree } from 'app/shared/model/degree.model';
import { DegreeService } from './degree.service';

@Component({
  selector: 'jhi-degree-delete-dialog',
  templateUrl: './degree-delete-dialog.component.html'
})
export class DegreeDeleteDialogComponent {
  degree: IDegree;

  constructor(protected degreeService: DegreeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.degreeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'degreeListModification',
        content: 'Deleted an degree'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-degree-delete-popup',
  template: ''
})
export class DegreeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ degree }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DegreeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.degree = degree;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/degree', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/degree', { outlets: { popup: null } }]);
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
