import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFunctionalRole, FunctionalRole } from 'app/shared/model/functional-role.model';
import { FunctionalRoleService } from './functional-role.service';

@Component({
  selector: 'jhi-functional-role-update',
  templateUrl: './functional-role-update.component.html'
})
export class FunctionalRoleUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    maxHolidays: [null, [Validators.required]],
    workingHours: [null, [Validators.required]]
  });

  constructor(protected functionalRoleService: FunctionalRoleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ functionalRole }) => {
      this.updateForm(functionalRole);
    });
  }

  updateForm(functionalRole: IFunctionalRole) {
    this.editForm.patchValue({
      id: functionalRole.id,
      name: functionalRole.name,
      description: functionalRole.description,
      maxHolidays: functionalRole.maxHolidays,
      workingHours: functionalRole.workingHours
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const functionalRole = this.createFromForm();
    if (functionalRole.id !== undefined) {
      this.subscribeToSaveResponse(this.functionalRoleService.update(functionalRole));
    } else {
      this.subscribeToSaveResponse(this.functionalRoleService.create(functionalRole));
    }
  }

  private createFromForm(): IFunctionalRole {
    return {
      ...new FunctionalRole(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      maxHolidays: this.editForm.get(['maxHolidays']).value,
      workingHours: this.editForm.get(['workingHours']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFunctionalRole>>) {
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
