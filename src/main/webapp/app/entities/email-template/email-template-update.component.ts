import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
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

  holidayEmail: string;

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
    this.holidayEmail = emailTemplate.holidayEmailTemplate;
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
      this.subscribeToSaveResponse(this.emailTemplateService.update(emailTemplate));
    }
  }

  private createFromForm(): IEmailTemplate {
    return {
      ...new EmailTemplate(),
      id: this.editForm.get(['id']).value,
      holidayEmailTemplate: this.editForm.get(['holidayEmailTemplate']).value
    };
  }

  insertVariable(text: string, oField: any) {
    if (oField.selectionStart || oField.selectionStart == '0') {
      oField.selectionStart;

      this.holidayEmail =
        this.holidayEmail.substr(0, oField.selectionStart) + '##' + text + '##' + this.holidayEmail.substr(oField.selectionStart);
    }
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
