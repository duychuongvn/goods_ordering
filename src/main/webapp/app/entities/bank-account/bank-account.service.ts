import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBankAccount } from 'app/shared/model/bank-account.model';

type EntityResponseType = HttpResponse<IBankAccount>;
type EntityArrayResponseType = HttpResponse<IBankAccount[]>;

@Injectable({ providedIn: 'root' })
export class BankAccountService {
  public resourceUrl = SERVER_API_URL + 'api/bank-accounts';

  constructor(protected http: HttpClient) {}

  create(bankAccount: IBankAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bankAccount);
    return this.http
      .post<IBankAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bankAccount: IBankAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bankAccount);
    return this.http
      .put<IBankAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBankAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBankAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bankAccount: IBankAccount): IBankAccount {
    const copy: IBankAccount = Object.assign({}, bankAccount, {
      createdAt: bankAccount.createdAt != null && bankAccount.createdAt.isValid() ? bankAccount.createdAt.toJSON() : null,
      lastUpdatedAt: bankAccount.lastUpdatedAt != null && bankAccount.lastUpdatedAt.isValid() ? bankAccount.lastUpdatedAt.toJSON() : null
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
      res.body.forEach((bankAccount: IBankAccount) => {
        bankAccount.createdAt = bankAccount.createdAt != null ? moment(bankAccount.createdAt) : null;
        bankAccount.lastUpdatedAt = bankAccount.lastUpdatedAt != null ? moment(bankAccount.lastUpdatedAt) : null;
      });
    }
    return res;
  }
}
