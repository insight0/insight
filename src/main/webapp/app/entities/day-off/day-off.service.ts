import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDayOff } from 'app/shared/model/day-off.model';

type EntityResponseType = HttpResponse<IDayOff>;
type EntityArrayResponseType = HttpResponse<IDayOff[]>;

@Injectable({ providedIn: 'root' })
export class DayOffService {
  public resourceUrl = SERVER_API_URL + 'api/day-offs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/day-offs';

  constructor(protected http: HttpClient) {}

  create(dayOff: IDayOff): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dayOff);
    return this.http
      .post<IDayOff>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dayOff: IDayOff): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dayOff);
    return this.http
      .put<IDayOff>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IDayOff>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDayOff[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDayOff[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(dayOff: IDayOff): IDayOff {
    const copy: IDayOff = Object.assign({}, dayOff, {
      startDate: dayOff.startDate != null && dayOff.startDate.isValid() ? dayOff.startDate.toJSON() : null,
      endDate: dayOff.endDate != null && dayOff.endDate.isValid() ? dayOff.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dayOff: IDayOff) => {
        dayOff.startDate = dayOff.startDate != null ? moment(dayOff.startDate) : null;
        dayOff.endDate = dayOff.endDate != null ? moment(dayOff.endDate) : null;
      });
    }
    return res;
  }
}
