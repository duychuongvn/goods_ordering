import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderLineItem } from 'app/shared/model/order-line-item.model';

type EntityResponseType = HttpResponse<IOrderLineItem>;
type EntityArrayResponseType = HttpResponse<IOrderLineItem[]>;

@Injectable({ providedIn: 'root' })
export class OrderLineItemService {
  public resourceUrl = SERVER_API_URL + 'api/order-line-items';

  constructor(protected http: HttpClient) {}

  create(orderLineItem: IOrderLineItem): Observable<EntityResponseType> {
    return this.http.post<IOrderLineItem>(this.resourceUrl, orderLineItem, { observe: 'response' });
  }

  update(orderLineItem: IOrderLineItem): Observable<EntityResponseType> {
    return this.http.put<IOrderLineItem>(this.resourceUrl, orderLineItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderLineItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderLineItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
