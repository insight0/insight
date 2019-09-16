import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalaryPackage } from 'app/shared/model/salary-package.model';
import { SalaryPackageService } from './salary-package.service';

@Component({
  selector: 'jhi-salary-package-delete-dialog',
  templateUrl: './salary-package-delete-dialog.component.html'
})
export class SalaryPackageDeleteDialogComponent {
  salaryPackage: ISalaryPackage;

  constructor(
    protected salaryPackageService: SalaryPackageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.salaryPackageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'salaryPackageListModification',
        content: 'Deleted an salaryPackage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-salary-package-delete-popup',
  template: ''
})
export class SalaryPackageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ salaryPackage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SalaryPackageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.salaryPackage = salaryPackage;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/salary-package', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/salary-package', { outlets: { popup: null } }]);
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
