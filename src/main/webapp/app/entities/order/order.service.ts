import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrder } from 'app/shared/model/order.model';

type EntityResponseType = HttpResponse<IOrder>;
type EntityArrayResponseType = HttpResponse<IOrder[]>;

@Injectable({ providedIn: 'root' })
export class OrderService {
  public resourceUrl = SERVER_API_URL + 'api/orders';

  constructor(protected http: HttpClient) {}

  create(order: IOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(order);
    return this.http
      .post<IOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(order: IOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(order);
    return this.http
      .put<IOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(order: IOrder): IOrder {
    const copy: IOrder = Object.assign({}, order, {
      orderDate: order.orderDate != null && order.orderDate.isValid() ? order.orderDate.toJSON() : null,
      packingDate: order.packingDate != null && order.packingDate.isValid() ? order.packingDate.format(DATE_FORMAT) : null,
      estimatedDeliverDate:
        order.estimatedDeliverDate != null && order.estimatedDeliverDate.isValid() ? order.estimatedDeliverDate.format(DATE_FORMAT) : null,
      deliveredDate: order.deliveredDate != null && order.deliveredDate.isValid() ? order.deliveredDate.format(DATE_FORMAT) : null,
      finishPaymentTime: order.finishPaymentTime != null && order.finishPaymentTime.isValid() ? order.finishPaymentTime.toJSON() : null,
      createdAt: order.createdAt != null && order.createdAt.isValid() ? order.createdAt.toJSON() : null,
      lastUpdatedAt: order.lastUpdatedAt != null && order.lastUpdatedAt.isValid() ? order.lastUpdatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.orderDate = res.body.orderDate != null ? moment(res.body.orderDate) : null;
      res.body.packingDate = res.body.packingDate != null ? moment(res.body.packingDate) : null;
      res.body.estimatedDeliverDate = res.body.estimatedDeliverDate != null ? moment(res.body.estimatedDeliverDate) : null;
      res.body.deliveredDate = res.body.deliveredDate != null ? moment(res.body.deliveredDate) : null;
      res.body.finishPaymentTime = res.body.finishPaymentTime != null ? moment(res.body.finishPaymentTime) : null;
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.lastUpdatedAt = res.body.lastUpdatedAt != null ? moment(res.body.lastUpdatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((order: IOrder) => {
        order.orderDate = order.orderDate != null ? moment(order.orderDate) : null;
        order.packingDate = order.packingDate != null ? moment(order.packingDate) : null;
        order.estimatedDeliverDate = order.estimatedDeliverDate != null ? moment(order.estimatedDeliverDate) : null;
        order.deliveredDate = order.deliveredDate != null ? moment(order.deliveredDate) : null;
        order.finishPaymentTime = order.finishPaymentTime != null ? moment(order.finishPaymentTime) : null;
        order.createdAt = order.createdAt != null ? moment(order.createdAt) : null;
        order.lastUpdatedAt = order.lastUpdatedAt != null ? moment(order.lastUpdatedAt) : null;
      });
    }
    return res;
  }
}
