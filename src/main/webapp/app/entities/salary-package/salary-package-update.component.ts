import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISalaryPackage, SalaryPackage } from 'app/shared/model/salary-package.model';
import { SalaryPackageService } from './salary-package.service';

@Component({
  selector: 'jhi-salary-package-update',
  templateUrl: './salary-package-update.component.html'
})
export class SalaryPackageUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    netSalaryPerMonth: [null, [Validators.required]],
    nationalInsurancePerMonth: [null, [Validators.required]],
    taxPerMonth: [null, [Validators.required]],
    privateInsurancePerMonth: [null, [Validators.required]],
    mealVoucherPerMonth: [null, [Validators.required]],
    bonusPerYear: [null, [Validators.required]]
  });

  constructor(protected salaryPackageService: SalaryPackageService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ salaryPackage }) => {
      this.updateForm(salaryPackage);
    });
  }

  updateForm(salaryPackage: ISalaryPackage) {
    this.editForm.patchValue({
      id: salaryPackage.id,
      netSalaryPerMonth: salaryPackage.netSalaryPerMonth,
      nationalInsurancePerMonth: salaryPackage.nationalInsurancePerMonth,
      taxPerMonth: salaryPackage.taxPerMonth,
      privateInsurancePerMonth: salaryPackage.privateInsurancePerMonth,
      mealVoucherPerMonth: salaryPackage.mealVoucherPerMonth,
      bonusPerYear: salaryPackage.bonusPerYear
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const salaryPackage = this.createFromForm();
    if (salaryPackage.id !== undefined) {
      this.subscribeToSaveResponse(this.salaryPackageService.update(salaryPackage));
    } else {
      this.subscribeToSaveResponse(this.salaryPackageService.create(salaryPackage));
    }
  }

  private createFromForm(): ISalaryPackage {
    return {
      ...new SalaryPackage(),
      id: this.editForm.get(['id']).value,
      netSalaryPerMonth: this.editForm.get(['netSalaryPerMonth']).value,
      nationalInsurancePerMonth: this.editForm.get(['nationalInsurancePerMonth']).value,
      taxPerMonth: this.editForm.get(['taxPerMonth']).value,
      privateInsurancePerMonth: this.editForm.get(['privateInsurancePerMonth']).value,
      mealVoucherPerMonth: this.editForm.get(['mealVoucherPerMonth']).value,
      bonusPerYear: this.editForm.get(['bonusPerYear']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalaryPackage>>) {
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
