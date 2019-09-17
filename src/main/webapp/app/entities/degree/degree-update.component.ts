import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDegree, Degree } from 'app/shared/model/degree.model';
import { DegreeService } from './degree.service';

@Component({
  selector: 'jhi-degree-update',
  templateUrl: './degree-update.component.html'
})
export class DegreeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    year: [null, [Validators.required]],
    duration: [],
    school: [null, [Validators.required]],
    filePath: [null, [Validators.required]]
  });

  constructor(protected degreeService: DegreeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ degree }) => {
      this.updateForm(degree);
    });
  }

  updateForm(degree: IDegree) {
    this.editForm.patchValue({
      id: degree.id,
      name: degree.name,
      year: degree.year,
      duration: degree.duration,
      school: degree.school,
      filePath: degree.filePath
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const degree = this.createFromForm();
    if (degree.id !== undefined) {
      this.subscribeToSaveResponse(this.degreeService.update(degree));
    } else {
      this.subscribeToSaveResponse(this.degreeService.create(degree));
    }
  }

  private createFromForm(): IDegree {
    return {
      ...new Degree(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      year: this.editForm.get(['year']).value,
      duration: this.editForm.get(['duration']).value,
      school: this.editForm.get(['school']).value,
      filePath: this.editForm.get(['filePath']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDegree>>) {
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
