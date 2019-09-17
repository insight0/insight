import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICertification } from 'app/shared/model/certification.model';

@Component({
  selector: 'jhi-certification-detail',
  templateUrl: './certification-detail.component.html'
})
export class CertificationDetailComponent implements OnInit {
  certification: ICertification;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ certification }) => {
      this.certification = certification;
    });
  }

  previousState() {
    window.history.back();
  }
}
