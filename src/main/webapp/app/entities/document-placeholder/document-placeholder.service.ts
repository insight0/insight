import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocumentPlaceholder } from 'app/shared/model/document-placeholder.model';

type EntityResponseType = HttpResponse<IDocumentPlaceholder>;
type EntityArrayResponseType = HttpResponse<IDocumentPlaceholder[]>;

@Injectable({ providedIn: 'root' })
export class DocumentPlaceholderService {
  public resourceUrl = SERVER_API_URL + 'api/document-placeholders';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/document-placeholders';

  constructor(protected http: HttpClient) {}

  create(documentPlaceholder: IDocumentPlaceholder): Observable<EntityResponseType> {
    return this.http.post<IDocumentPlaceholder>(this.resourceUrl, documentPlaceholder, { observe: 'response' });
  }

  update(documentPlaceholder: IDocumentPlaceholder): Observable<EntityResponseType> {
    return this.http.put<IDocumentPlaceholder>(this.resourceUrl, documentPlaceholder, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IDocumentPlaceholder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumentPlaceholder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumentPlaceholder[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
