import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalaryPackage } from 'app/shared/model/salary-package.model';

@Component({
  selector: 'jhi-salary-package-detail',
  templateUrl: './salary-package-detail.component.html'
})
export class SalaryPackageDetailComponent implements OnInit {
  salaryPackage: ISalaryPackage;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ salaryPackage }) => {
      this.salaryPackage = salaryPackage;
    });
  }

  previousState() {
    window.history.back();
  }
}
