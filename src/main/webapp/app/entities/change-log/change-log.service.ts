import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChangeLog } from 'app/shared/model/change-log.model';

type EntityResponseType = HttpResponse<IChangeLog>;
type EntityArrayResponseType = HttpResponse<IChangeLog[]>;

@Injectable({ providedIn: 'root' })
export class ChangeLogService {
  public resourceUrl = SERVER_API_URL + 'api/change-logs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/change-logs';

  constructor(protected http: HttpClient) {}

  create(changeLog: IChangeLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(changeLog);
    return this.http
      .post<IChangeLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(changeLog: IChangeLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(changeLog);
    return this.http
      .put<IChangeLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IChangeLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChangeLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChangeLog[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(changeLog: IChangeLog): IChangeLog {
    const copy: IChangeLog = Object.assign({}, changeLog, {
      date: changeLog.date != null && changeLog.date.isValid() ? changeLog.date.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((changeLog: IChangeLog) => {
        changeLog.date = changeLog.date != null ? moment(changeLog.date) : null;
      });
    }
    return res;
  }
}
