import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPayment } from 'app/shared/model/payment.model';

type EntityResponseType = HttpResponse<IPayment>;
type EntityArrayResponseType = HttpResponse<IPayment[]>;

@Injectable({ providedIn: 'root' })
export class PaymentService {
  public resourceUrl = SERVER_API_URL + 'api/payments';

  constructor(protected http: HttpClient) {}

  create(payment: IPayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payment);
    return this.http
      .post<IPayment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(payment: IPayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payment);
    return this.http
      .put<IPayment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPayment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPayment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(payment: IPayment): IPayment {
    const copy: IPayment = Object.assign({}, payment, {
      paidTime: payment.paidTime != null && payment.paidTime.isValid() ? payment.paidTime.toJSON() : null,
      createdAt: payment.createdAt != null && payment.createdAt.isValid() ? payment.createdAt.toJSON() : null,
      lastUpdatedAt: payment.lastUpdatedAt != null && payment.lastUpdatedAt.isValid() ? payment.lastUpdatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.paidTime = res.body.paidTime != null ? moment(res.body.paidTime) : null;
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.lastUpdatedAt = res.body.lastUpdatedAt != null ? moment(res.body.lastUpdatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((payment: IPayment) => {
        payment.paidTime = payment.paidTime != null ? moment(payment.paidTime) : null;
        payment.createdAt = payment.createdAt != null ? moment(payment.createdAt) : null;
        payment.lastUpdatedAt = payment.lastUpdatedAt != null ? moment(payment.lastUpdatedAt) : null;
      });
    }
    return res;
  }
}
