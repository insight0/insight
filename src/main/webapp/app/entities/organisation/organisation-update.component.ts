import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IOrganisation, Organisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from './organisation.service';

@Component({
  selector: 'jhi-organisation-update',
  templateUrl: './organisation-update.component.html'
})
export class OrganisationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    phoneNumber: [],
    siteUrl: [],
    logoUrl: [],
    email: []
  });

  constructor(protected organisationService: OrganisationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ organisation }) => {
      this.updateForm(organisation);
    });
  }

  updateForm(organisation: IOrganisation) {
    this.editForm.patchValue({
      id: organisation.id,
      name: organisation.name,
      phoneNumber: organisation.phoneNumber,
      siteUrl: organisation.siteUrl,
      logoUrl: organisation.logoUrl,
      email: organisation.email
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const organisation = this.createFromForm();
    if (organisation.id !== undefined) {
      this.subscribeToSaveResponse(this.organisationService.update(organisation));
    } else {
      this.subscribeToSaveResponse(this.organisationService.create(organisation));
    }
  }

  private createFromForm(): IOrganisation {
    return {
      ...new Organisation(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      siteUrl: this.editForm.get(['siteUrl']).value,
      logoUrl: this.editForm.get(['logoUrl']).value,
      email: this.editForm.get(['email']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisation>>) {
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
