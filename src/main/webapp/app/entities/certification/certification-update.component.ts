import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICertification, Certification } from 'app/shared/model/certification.model';
import { CertificationService } from './certification.service';

@Component({
  selector: 'jhi-certification-update',
  templateUrl: './certification-update.component.html'
})
export class CertificationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    year: [null, [Validators.required]],
    organization: [null, [Validators.required]],
    filePath: []
  });

  constructor(protected certificationService: CertificationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ certification }) => {
      this.updateForm(certification);
    });
  }

  updateForm(certification: ICertification) {
    this.editForm.patchValue({
      id: certification.id,
      name: certification.name,
      year: certification.year,
      organization: certification.organization,
      filePath: certification.filePath
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const certification = this.createFromForm();
    if (certification.id !== undefined) {
      this.subscribeToSaveResponse(this.certificationService.update(certification));
    } else {
      this.subscribeToSaveResponse(this.certificationService.create(certification));
    }
  }

  private createFromForm(): ICertification {
    return {
      ...new Certification(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      year: this.editForm.get(['year']).value,
      organization: this.editForm.get(['organization']).value,
      filePath: this.editForm.get(['filePath']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICertification>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
