import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IChangeLog, ChangeLog } from 'app/shared/model/change-log.model';
import { ChangeLogService } from './change-log.service';

@Component({
  selector: 'jhi-change-log-update',
  templateUrl: './change-log-update.component.html'
})
export class ChangeLogUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    date: [null, [Validators.required]],
    changeTrigger: [null, [Validators.required]],
    changeType: [null, [Validators.required]],
    description: []
  });

  constructor(protected changeLogService: ChangeLogService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ changeLog }) => {
      this.updateForm(changeLog);
    });
  }

  updateForm(changeLog: IChangeLog) {
    this.editForm.patchValue({
      id: changeLog.id,
      userId: changeLog.userId,
      date: changeLog.date != null ? changeLog.date.format(DATE_TIME_FORMAT) : null,
      changeTrigger: changeLog.changeTrigger,
      changeType: changeLog.changeType,
      description: changeLog.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const changeLog = this.createFromForm();
    if (changeLog.id !== undefined) {
      this.subscribeToSaveResponse(this.changeLogService.update(changeLog));
    } else {
      this.subscribeToSaveResponse(this.changeLogService.create(changeLog));
    }
  }

  private createFromForm(): IChangeLog {
    return {
      ...new ChangeLog(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      changeTrigger: this.editForm.get(['changeTrigger']).value,
      changeType: this.editForm.get(['changeType']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChangeLog>>) {
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
