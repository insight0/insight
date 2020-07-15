import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { IFormation } from 'app/shared/model/formation.model';
import { FormationService } from 'app/entities/formation/formation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbModalStack } from '@ng-bootstrap/ng-bootstrap/modal/modal-stack';

@Component({
  selector: 'jhi-formation-delete-dialog',
  templateUrl: 'formation-detail.component.html'
})
export class FormationDeleteDialogComponent implements OnInit, OnDestroy {
  protected ngbModal: NgbModal;

  constructor() {}

  ngOnInit(): void {}

  ngOnDestroy() {}
}

@Component({
  selector: 'jhi-foramtion-delte-popp',
  template: ''
})
export class FormationDeletePopup implements OnInit, OnDestroy {
  protected ngbModal: NgbModal;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ formation }) => {
      setTimeout(() => {
        this.ngbModal;
      });
    });
  }

  ngOnDestroy() {
    this.ngbModal = null;
  }
}
