import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IConfig, Config } from 'app/shared/model/config.model';
import { ConfigService } from './config.service';

@Component({
  selector: 'jhi-config-update',
  templateUrl: './config-update.component.html'
})
export class ConfigUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    holidayEmailSend: [],
    newUserWelcommingEmail: [null, [Validators.required]]
  });

  constructor(protected configService: ConfigService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ config }) => {
      this.updateForm(config);
    });
  }

  updateForm(config: IConfig) {
    this.editForm.patchValue({
      id: config.id,
      holidayEmailSend: config.holidayEmailSend,
      newUserWelcommingEmail: config.newUserWelcommingEmail
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const config = this.createFromForm();
    if (config.id !== undefined) {
      this.subscribeToSaveResponse(this.configService.update(config));
    } else {
      this.subscribeToSaveResponse(this.configService.create(config));
    }
  }

  private createFromForm(): IConfig {
    return {
      ...new Config(),
      id: this.editForm.get(['id']).value,
      holidayEmailSend: this.editForm.get(['holidayEmailSend']).value,
      newUserWelcommingEmail: this.editForm.get(['newUserWelcommingEmail']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConfig>>) {
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
