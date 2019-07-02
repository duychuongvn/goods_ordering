import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderSchedule } from 'app/shared/model/order-schedule.model';

type EntityResponseType = HttpResponse<IOrderSchedule>;
type EntityArrayResponseType = HttpResponse<IOrderSchedule[]>;

@Injectable({ providedIn: 'root' })
export class OrderScheduleService {
  public resourceUrl = SERVER_API_URL + 'api/order-schedules';

  constructor(protected http: HttpClient) {}

  create(orderSchedule: IOrderSchedule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderSchedule);
    return this.http
      .post<IOrderSchedule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderSchedule: IOrderSchedule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderSchedule);
    return this.http
      .put<IOrderSchedule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderSchedule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderSchedule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orderSchedule: IOrderSchedule): IOrderSchedule {
    const copy: IOrderSchedule = Object.assign({}, orderSchedule, {
      openDate: orderSchedule.openDate != null && orderSchedule.openDate.isValid() ? orderSchedule.openDate.format(DATE_FORMAT) : null,
      closeDate: orderSchedule.closeDate != null && orderSchedule.closeDate.isValid() ? orderSchedule.closeDate.format(DATE_FORMAT) : null,
      expectedPackingDate:
        orderSchedule.expectedPackingDate != null && orderSchedule.expectedPackingDate.isValid()
          ? orderSchedule.expectedPackingDate.format(DATE_FORMAT)
          : null,
      expectedDeliveryDate:
        orderSchedule.expectedDeliveryDate != null && orderSchedule.expectedDeliveryDate.isValid()
          ? orderSchedule.expectedDeliveryDate.format(DATE_FORMAT)
          : null,
      createdAt: orderSchedule.createdAt != null && orderSchedule.createdAt.isValid() ? orderSchedule.createdAt.toJSON() : null,
      lastUpdatedAt:
        orderSchedule.lastUpdatedAt != null && orderSchedule.lastUpdatedAt.isValid() ? orderSchedule.lastUpdatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.openDate = res.body.openDate != null ? moment(res.body.openDate) : null;
      res.body.closeDate = res.body.closeDate != null ? moment(res.body.closeDate) : null;
      res.body.expectedPackingDate = res.body.expectedPackingDate != null ? moment(res.body.expectedPackingDate) : null;
      res.body.expectedDeliveryDate = res.body.expectedDeliveryDate != null ? moment(res.body.expectedDeliveryDate) : null;
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.lastUpdatedAt = res.body.lastUpdatedAt != null ? moment(res.body.lastUpdatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderSchedule: IOrderSchedule) => {
        orderSchedule.openDate = orderSchedule.openDate != null ? moment(orderSchedule.openDate) : null;
        orderSchedule.closeDate = orderSchedule.closeDate != null ? moment(orderSchedule.closeDate) : null;
        orderSchedule.expectedPackingDate = orderSchedule.expectedPackingDate != null ? moment(orderSchedule.expectedPackingDate) : null;
        orderSchedule.expectedDeliveryDate = orderSchedule.expectedDeliveryDate != null ? moment(orderSchedule.expectedDeliveryDate) : null;
        orderSchedule.createdAt = orderSchedule.createdAt != null ? moment(orderSchedule.createdAt) : null;
        orderSchedule.lastUpdatedAt = orderSchedule.lastUpdatedAt != null ? moment(orderSchedule.lastUpdatedAt) : null;
      });
    }
    return res;
  }
}
