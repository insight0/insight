import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { FormationService } from 'app/entities/formation/formation.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { Formation, IFormation } from 'app/shared/model/formation.model';
import { FunctionalRoleService } from 'app/entities/functional-role';

@Component({
  selector: 'jhi-formation-update',
  templateUrl: 'formation-update.component.html',
  styleUrls: ['formation.scss']
})
export class FormationUpdateComponent implements OnInit, OnDestroy {
  isSaving: boolean = false;
  totalItems: any;
  rolesTitles: string[];
  loading: boolean;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    description: [null, [Validators.required]],
    society: [null, [Validators.required]],
    location: [null, [Validators.required]],
    concerned: [null, [Validators.required]],
    date: [null],
    candidate: [null]
  });

  constructor(
    protected formationService: FormationService,
    protected functionalRoleService: FunctionalRoleService,
    protected fb: FormBuilder,
    protected activatedRoute: ActivatedRoute
  ) {}

  updateForm(formation: IFormation) {
    this.editForm.patchValue({
      id: formation.id,
      title: formation.title,
      society: formation.society,
      concerned: formation.concerned,
      location: formation.location,
      description: formation.description
    });
  }

  private createFromForm(): IFormation {
    return {
      ...new Formation(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      society: this.editForm.get(['society']).value,
      concerned: this.editForm.get(['concerned']).value,
      location: this.editForm.get(['location']).value,
      description: this.editForm.get(['description']).value
    };
  }

  ngOnDestroy(): void {}

  ngOnInit(): void {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ formation }) => {
      this.updateForm(formation);
    });
    this.getRolesTitles();
  }

  save() {
    this.isSaving = true;
    const formation = this.createFromForm();
    if (formation.id == undefined) {
      this.subscribeToSaveResponse(this.formationService.create(formation));
    } else {
      this.subscribeToSaveResponse(this.formationService.update(formation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormation>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  previousState() {
    window.history.back();
  }

  getRolesTitles() {
    this.functionalRoleService.getRolestitles().subscribe(res => (this.rolesTitles = res));
  }
}
