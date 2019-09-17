import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDegree } from 'app/shared/model/degree.model';

@Component({
  selector: 'jhi-degree-detail',
  templateUrl: './degree-detail.component.html'
})
export class DegreeDetailComponent implements OnInit {
  degree: IDegree;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ degree }) => {
      this.degree = degree;
    });
  }

  previousState() {
    window.history.back();
  }
}
