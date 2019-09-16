import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IDayOff, DayOff } from 'app/shared/model/day-off.model';
import { DayOffService } from './day-off.service';

@Component({
  selector: 'jhi-day-off-update',
  templateUrl: './day-off-update.component.html'
})
export class DayOffUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    dayOffObject: [null, [Validators.required]],
    status: [],
    forced: [null, [Validators.required]],
    employeId: [null, [Validators.required]],
    validatorId: [null, [Validators.required]],
    days: [null, [Validators.required]]
  });

  constructor(protected dayOffService: DayOffService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dayOff }) => {
      this.updateForm(dayOff);
    });
  }

  updateForm(dayOff: IDayOff) {
    this.editForm.patchValue({
      id: dayOff.id,
      startDate: dayOff.startDate != null ? dayOff.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: dayOff.endDate != null ? dayOff.endDate.format(DATE_TIME_FORMAT) : null,
      dayOffObject: dayOff.dayOffObject,
      status: dayOff.status,
      forced: dayOff.forced,
      employeId: dayOff.employeId,
      validatorId: dayOff.validatorId,
      days: dayOff.days
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dayOff = this.createFromForm();
    if (dayOff.id !== undefined) {
      this.subscribeToSaveResponse(this.dayOffService.update(dayOff));
    } else {
      this.subscribeToSaveResponse(this.dayOffService.create(dayOff));
    }
  }

  private createFromForm(): IDayOff {
    return {
      ...new DayOff(),
      id: this.editForm.get(['id']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      dayOffObject: this.editForm.get(['dayOffObject']).value,
      status: this.editForm.get(['status']).value,
      forced: this.editForm.get(['forced']).value,
      employeId: this.editForm.get(['employeId']).value,
      validatorId: this.editForm.get(['validatorId']).value,
      days: this.editForm.get(['days']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDayOff>>) {
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