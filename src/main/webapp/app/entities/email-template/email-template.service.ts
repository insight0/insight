import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmailTemplate } from 'app/shared/model/email-template.model';

type EntityResponseType = HttpResponse<IEmailTemplate>;
type EntityArrayResponseType = HttpResponse<IEmailTemplate[]>;

@Injectable({ providedIn: 'root' })
export class EmailTemplateService {
  public resourceUrl = SERVER_API_URL + 'api/email-templates';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/email-templates';

  constructor(protected http: HttpClient) {}

  update(emailTemplate: IEmailTemplate): Observable<EntityResponseType> {
    return this.http.put<IEmailTemplate>(this.resourceUrl, emailTemplate, { observe: 'response' });
  }

  find(): Observable<EntityResponseType> {
    return this.http.get<IEmailTemplate>(`${this.resourceUrl}`, { observe: 'response' });
  }
}
