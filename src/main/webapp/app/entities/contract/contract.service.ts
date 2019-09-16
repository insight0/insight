import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContract } from 'app/shared/model/contract.model';

type EntityResponseType = HttpResponse<IContract>;
type EntityArrayResponseType = HttpResponse<IContract[]>;

@Injectable({ providedIn: 'root' })
export class ContractService {
  public resourceUrl = SERVER_API_URL + 'api/contracts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/contracts';

  constructor(protected http: HttpClient) {}

  create(contract: IContract): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contract);
    return this.http
      .post<IContract>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contract: IContract): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contract);
    return this.http
      .put<IContract>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IContract>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContract[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContract[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(contract: IContract): IContract {
    const copy: IContract = Object.assign({}, contract, {
      startDate: contract.startDate != null && contract.startDate.isValid() ? contract.startDate.toJSON() : null,
      endDate: contract.endDate != null && contract.endDate.isValid() ? contract.endDate.toJSON() : null,
      employeeSignatureDate:
        contract.employeeSignatureDate != null && contract.employeeSignatureDate.isValid() ? contract.employeeSignatureDate.toJSON() : null,
      directionSignatureDate:
        contract.directionSignatureDate != null && contract.directionSignatureDate.isValid()
          ? contract.directionSignatureDate.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
      res.body.employeeSignatureDate = res.body.employeeSignatureDate != null ? moment(res.body.employeeSignatureDate) : null;
      res.body.directionSignatureDate = res.body.directionSignatureDate != null ? moment(res.body.directionSignatureDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contract: IContract) => {
        contract.startDate = contract.startDate != null ? moment(contract.startDate) : null;
        contract.endDate = contract.endDate != null ? moment(contract.endDate) : null;
        contract.employeeSignatureDate = contract.employeeSignatureDate != null ? moment(contract.employeeSignatureDate) : null;
        contract.directionSignatureDate = contract.directionSignatureDate != null ? moment(contract.directionSignatureDate) : null;
      });
    }
    return res;
  }
}
