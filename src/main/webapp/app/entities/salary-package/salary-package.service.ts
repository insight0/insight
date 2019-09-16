import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalaryPackage } from 'app/shared/model/salary-package.model';

type EntityResponseType = HttpResponse<ISalaryPackage>;
type EntityArrayResponseType = HttpResponse<ISalaryPackage[]>;

@Injectable({ providedIn: 'root' })
export class SalaryPackageService {
  public resourceUrl = SERVER_API_URL + 'api/salary-packages';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/salary-packages';

  constructor(protected http: HttpClient) {}

  create(salaryPackage: ISalaryPackage): Observable<EntityResponseType> {
    return this.http.post<ISalaryPackage>(this.resourceUrl, salaryPackage, { observe: 'response' });
  }

  update(salaryPackage: ISalaryPackage): Observable<EntityResponseType> {
    return this.http.put<ISalaryPackage>(this.resourceUrl, salaryPackage, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISalaryPackage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISalaryPackage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISalaryPackage[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
