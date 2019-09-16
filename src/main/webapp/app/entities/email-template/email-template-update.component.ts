import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEmailTemplate, EmailTemplate } from 'app/shared/model/email-template.model';
import { EmailTemplateService } from './email-template.service';

@Component({
  selector: 'jhi-email-template-update',
  templateUrl: './email-template-update.component.html'
})
export class EmailTemplateUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    holidayEmailTemplate: []
  });

  constructor(protected emailTemplateService: EmailTemplateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ emailTemplate }) => {
      this.updateForm(emailTemplate);
    });
  }

  updateForm(emailTemplate: IEmailTemplate) {
    this.editForm.patchValue({
      id: emailTemplate.id,
      holidayEmailTemplate: emailTemplate.holidayEmailTemplate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const emailTemplate = this.createFromForm();
    if (emailTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.emailTemplateService.update(emailTemplate));
    } else {
      this.subscribeToSaveResponse(this.emailTemplateService.create(emailTemplate));
    }
  }

  private createFromForm(): IEmailTemplate {
    return {
      ...new EmailTemplate(),
      id: this.editForm.get(['id']).value,
      holidayEmailTemplate: this.editForm.get(['holidayEmailTemplate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmailTemplate>>) {
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
