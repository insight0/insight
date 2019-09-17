import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICertification } from 'app/shared/model/certification.model';
import { CertificationService } from './certification.service';

@Component({
  selector: 'jhi-certification-delete-dialog',
  templateUrl: './certification-delete-dialog.component.html'
})
export class CertificationDeleteDialogComponent {
  certification: ICertification;

  constructor(
    protected certificationService: CertificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.certificationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'certificationListModification',
        content: 'Deleted an certification'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-certification-delete-popup',
  template: ''
})
export class CertificationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ certification }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CertificationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.certification = certification;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/certification', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/certification', { outlets: { popup: null } }]);
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
