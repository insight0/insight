import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFunctionalRole } from 'app/shared/model/functional-role.model';

@Component({
  selector: 'jhi-functional-role-detail',
  templateUrl: './functional-role-detail.component.html'
})
export class FunctionalRoleDetailComponent implements OnInit {
  functionalRole: IFunctionalRole;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ functionalRole }) => {
      this.functionalRole = functionalRole;
    });
  }

  previousState() {
    window.history.back();
  }
}
