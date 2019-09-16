import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
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
    holidayEmailSendDate: [],
    holidayEmailNotification: [null, [Validators.required]],
    welcomingEmailNotification: [null, [Validators.required]],
    birthdayEmailNotification: [null, [Validators.required]]
  });
  activeHolidayNotif: boolean;

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
      holidayEmailSendDate: config.holidayEmailSendDate,
      holidayEmailNotification: config.holidayEmailNotification,
      welcomingEmailNotification: config.welcomingEmailNotification,
      birthdayEmailNotification: config.birthdayEmailNotification
    });
    this.activeHolidayNotif = config.holidayEmailNotification;
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
      config.id = 'DEFAULT_CONFIG';
      this.subscribeToSaveResponse(this.configService.update(config));
    }
  }

  private createFromForm(): IConfig {
    return {
      ...new Config(),
      id: this.editForm.get(['id']).value,
      holidayEmailSendDate: this.editForm.get(['holidayEmailSendDate']).value,
      holidayEmailNotification: this.editForm.get(['holidayEmailNotification']).value,
      welcomingEmailNotification: this.editForm.get(['welcomingEmailNotification']).value,
      birthdayEmailNotification: this.editForm.get(['birthdayEmailNotification']).value
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
