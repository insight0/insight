import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFunctionalRole } from 'app/shared/model/functional-role.model';
import { FunctionalRoleService } from './functional-role.service';

@Component({
  selector: 'jhi-functional-role-delete-dialog',
  templateUrl: './functional-role-delete-dialog.component.html'
})
export class FunctionalRoleDeleteDialogComponent {
  functionalRole: IFunctionalRole;

  constructor(
    protected functionalRoleService: FunctionalRoleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.functionalRoleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'functionalRoleListModification',
        content: 'Deleted an functionalRole'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-functional-role-delete-popup',
  template: ''
})
export class FunctionalRoleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ functionalRole }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FunctionalRoleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.functionalRole = functionalRole;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/functional-role', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/functional-role', { outlets: { popup: null } }]);
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
