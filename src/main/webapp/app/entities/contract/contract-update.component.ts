import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IContract, Contract } from 'app/shared/model/contract.model';
import { ContractService } from './contract.service';

@Component({
  selector: 'jhi-contract-update',
  templateUrl: './contract-update.component.html'
})
export class ContractUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [],
    employeeSignatureDate: [],
    directionSignatureDate: [],
    description: [],
    dayOffNamber: [null, [Validators.required]]
  });

  constructor(protected contractService: ContractService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contract }) => {
      this.updateForm(contract);
    });
  }

  updateForm(contract: IContract) {
    this.editForm.patchValue({
      id: contract.id,
      type: contract.type,
      startDate: contract.startDate != null ? contract.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: contract.endDate != null ? contract.endDate.format(DATE_TIME_FORMAT) : null,
      employeeSignatureDate: contract.employeeSignatureDate != null ? contract.employeeSignatureDate.format(DATE_TIME_FORMAT) : null,
      directionSignatureDate: contract.directionSignatureDate != null ? contract.directionSignatureDate.format(DATE_TIME_FORMAT) : null,
      description: contract.description,
      dayOffNamber: contract.dayOffNamber
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contract = this.createFromForm();
    if (contract.id !== undefined) {
      this.subscribeToSaveResponse(this.contractService.update(contract));
    } else {
      this.subscribeToSaveResponse(this.contractService.create(contract));
    }
  }

  private createFromForm(): IContract {
    return {
      ...new Contract(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      employeeSignatureDate:
        this.editForm.get(['employeeSignatureDate']).value != null
          ? moment(this.editForm.get(['employeeSignatureDate']).value, DATE_TIME_FORMAT)
          : undefined,
      directionSignatureDate:
        this.editForm.get(['directionSignatureDate']).value != null
          ? moment(this.editForm.get(['directionSignatureDate']).value, DATE_TIME_FORMAT)
          : undefined,
      description: this.editForm.get(['description']).value,
      dayOffNamber: this.editForm.get(['dayOffNamber']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContract>>) {
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
