import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentPlaceholder } from 'app/shared/model/document-placeholder.model';
import { DocumentPlaceholderService } from './document-placeholder.service';

@Component({
  selector: 'jhi-document-placeholder-delete-dialog',
  templateUrl: './document-placeholder-delete-dialog.component.html'
})
export class DocumentPlaceholderDeleteDialogComponent {
  documentPlaceholder: IDocumentPlaceholder;

  constructor(
    protected documentPlaceholderService: DocumentPlaceholderService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.documentPlaceholderService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'documentPlaceholderListModification',
        content: 'Deleted an documentPlaceholder'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-document-placeholder-delete-popup',
  template: ''
})
export class DocumentPlaceholderDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ documentPlaceholder }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DocumentPlaceholderDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.documentPlaceholder = documentPlaceholder;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/document-placeholder', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/document-placeholder', { outlets: { popup: null } }]);
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
