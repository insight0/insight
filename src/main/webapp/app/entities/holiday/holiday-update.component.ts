import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IHoliday, Holiday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';

@Component({
  selector: 'jhi-holiday-update',
  templateUrl: './holiday-update.component.html',
  styleUrls: ['./holiday.component.scss']
})
export class HolidayUpdateComponent implements OnInit {
  isSaving: boolean;
  holiday: IHoliday;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    year: [null, []],
    dayOfYear: [null, []],
    weekOfYear: [null, []],
    dayOfWeek: [null, []],
    paid: [null, [Validators.required]],
    date: [null, [Validators.required]],
    duration: [null, [Validators.required]]
  });

  constructor(protected holidayService: HolidayService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ holiday }) => {
      this.updateForm(holiday);
    });
  }

  updateForm(holiday: IHoliday) {
    this.editForm.patchValue({
      id: holiday.id,
      name: holiday.name,
      description: holiday.description,
      year: holiday.year,
      dayOfYear: holiday.dayOfYear,
      weekOfYear: holiday.weekOfYear,
      dayOfWeek: holiday.dayOfWeek,
      paid: holiday.paid,
      date: holiday.date,
      duration: holiday.duration
    });
    this.holiday = holiday;
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const holiday = this.createFromForm();

    console.log(holiday);

    if (holiday.id !== undefined) {
      this.subscribeToSaveResponse(this.holidayService.update(holiday));
    } else {
      this.subscribeToSaveResponse(this.holidayService.create(holiday));
    }
  }

  private createFromForm(): IHoliday {
    return {
      ...new Holiday(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      year: this.editForm.get(['year']).value,
      dayOfYear: this.editForm.get(['dayOfYear']).value,
      weekOfYear: this.editForm.get(['weekOfYear']).value,
      dayOfWeek: this.editForm.get(['dayOfWeek']).value,
      paid: this.editForm.get(['paid']).value,
      duration: this.editForm.get(['duration']).value,
      date: this.editForm.get(['date']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHoliday>>) {
    result.subscribe(() => this.onSaveSuccess(), e => this.onSaveError(e));
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(e) {
    console.log(e);
    this.isSaving = false;
  }
}
