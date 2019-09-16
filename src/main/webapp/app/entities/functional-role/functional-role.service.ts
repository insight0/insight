import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFunctionalRole } from 'app/shared/model/functional-role.model';

type EntityResponseType = HttpResponse<IFunctionalRole>;
type EntityArrayResponseType = HttpResponse<IFunctionalRole[]>;

@Injectable({ providedIn: 'root' })
export class FunctionalRoleService {
  public resourceUrl = SERVER_API_URL + 'api/functional-roles';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/functional-roles';

  constructor(protected http: HttpClient) {}

  create(functionalRole: IFunctionalRole): Observable<EntityResponseType> {
    return this.http.post<IFunctionalRole>(this.resourceUrl, functionalRole, { observe: 'response' });
  }

  update(functionalRole: IFunctionalRole): Observable<EntityResponseType> {
    return this.http.put<IFunctionalRole>(this.resourceUrl, functionalRole, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IFunctionalRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFunctionalRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFunctionalRole[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
