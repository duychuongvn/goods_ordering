import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExchangeRate } from 'app/shared/model/exchange-rate.model';

type EntityResponseType = HttpResponse<IExchangeRate>;
type EntityArrayResponseType = HttpResponse<IExchangeRate[]>;

@Injectable({ providedIn: 'root' })
export class ExchangeRateService {
  public resourceUrl = SERVER_API_URL + 'api/exchange-rates';

  constructor(protected http: HttpClient) {}

  create(exchangeRate: IExchangeRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exchangeRate);
    return this.http
      .post<IExchangeRate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(exchangeRate: IExchangeRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exchangeRate);
    return this.http
      .put<IExchangeRate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExchangeRate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExchangeRate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(exchangeRate: IExchangeRate): IExchangeRate {
    const copy: IExchangeRate = Object.assign({}, exchangeRate, {
      createdAt: exchangeRate.createdAt != null && exchangeRate.createdAt.isValid() ? exchangeRate.createdAt.toJSON() : null,
      lastUpdatedAt: exchangeRate.lastUpdatedAt != null && exchangeRate.lastUpdatedAt.isValid() ? exchangeRate.lastUpdatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.lastUpdatedAt = res.body.lastUpdatedAt != null ? moment(res.body.lastUpdatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((exchangeRate: IExchangeRate) => {
        exchangeRate.createdAt = exchangeRate.createdAt != null ? moment(exchangeRate.createdAt) : null;
        exchangeRate.lastUpdatedAt = exchangeRate.lastUpdatedAt != null ? moment(exchangeRate.lastUpdatedAt) : null;
      });
    }
    return res;
  }
}
