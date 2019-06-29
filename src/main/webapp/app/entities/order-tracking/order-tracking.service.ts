import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderTracking } from 'app/shared/model/order-tracking.model';

type EntityResponseType = HttpResponse<IOrderTracking>;
type EntityArrayResponseType = HttpResponse<IOrderTracking[]>;

@Injectable({ providedIn: 'root' })
export class OrderTrackingService {
  public resourceUrl = SERVER_API_URL + 'api/order-trackings';

  constructor(protected http: HttpClient) {}

  create(orderTracking: IOrderTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderTracking);
    return this.http
      .post<IOrderTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderTracking: IOrderTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderTracking);
    return this.http
      .put<IOrderTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderTracking>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderTracking[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orderTracking: IOrderTracking): IOrderTracking {
    const copy: IOrderTracking = Object.assign({}, orderTracking, {
      dateTime: orderTracking.dateTime != null && orderTracking.dateTime.isValid() ? orderTracking.dateTime.toJSON() : null,
      createdAt: orderTracking.createdAt != null && orderTracking.createdAt.isValid() ? orderTracking.createdAt.toJSON() : null,
      lastUpdatedAt:
        orderTracking.lastUpdatedAt != null && orderTracking.lastUpdatedAt.isValid() ? orderTracking.lastUpdatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateTime = res.body.dateTime != null ? moment(res.body.dateTime) : null;
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.lastUpdatedAt = res.body.lastUpdatedAt != null ? moment(res.body.lastUpdatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderTracking: IOrderTracking) => {
        orderTracking.dateTime = orderTracking.dateTime != null ? moment(orderTracking.dateTime) : null;
        orderTracking.createdAt = orderTracking.createdAt != null ? moment(orderTracking.createdAt) : null;
        orderTracking.lastUpdatedAt = orderTracking.lastUpdatedAt != null ? moment(orderTracking.lastUpdatedAt) : null;
      });
    }
    return res;
  }
}
