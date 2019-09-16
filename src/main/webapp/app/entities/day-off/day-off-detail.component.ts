import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDayOff } from 'app/shared/model/day-off.model';

@Component({
  selector: 'jhi-day-off-detail',
  templateUrl: './day-off-detail.component.html'
})
export class DayOffDetailComponent implements OnInit {
  dayOff: IDayOff;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dayOff }) => {
      this.dayOff = dayOff;
    });
  }

  previousState() {
    window.history.back();
  }
}
