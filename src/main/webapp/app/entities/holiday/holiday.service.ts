import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHoliday } from 'app/shared/model/holiday.model';

type EntityResponseType = HttpResponse<IHoliday>;
type EntityArrayResponseType = HttpResponse<IHoliday[]>;

@Injectable({ providedIn: 'root' })
export class HolidayService {
  public resourceUrl = SERVER_API_URL + 'api/holidays';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/holidays';

  constructor(protected http: HttpClient) {}

  create(holiday: IHoliday): Observable<EntityResponseType> {
    return this.http.post<IHoliday>(this.resourceUrl, holiday, { observe: 'response' });
  }

  update(holiday: IHoliday): Observable<EntityResponseType> {
    return this.http.put<IHoliday>(this.resourceUrl, holiday, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IHoliday>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHoliday[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHoliday[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
