import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShippingAddress } from 'app/shared/model/shipping-address.model';

type EntityResponseType = HttpResponse<IShippingAddress>;
type EntityArrayResponseType = HttpResponse<IShippingAddress[]>;

@Injectable({ providedIn: 'root' })
export class ShippingAddressService {
  public resourceUrl = SERVER_API_URL + 'api/shipping-addresses';

  constructor(protected http: HttpClient) {}

  create(shippingAddress: IShippingAddress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shippingAddress);
    return this.http
      .post<IShippingAddress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(shippingAddress: IShippingAddress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shippingAddress);
    return this.http
      .put<IShippingAddress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IShippingAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IShippingAddress[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(shippingAddress: IShippingAddress): IShippingAddress {
    const copy: IShippingAddress = Object.assign({}, shippingAddress, {
      createdAt: shippingAddress.createdAt != null && shippingAddress.createdAt.isValid() ? shippingAddress.createdAt.toJSON() : null,
      lastUpdatedAt:
        shippingAddress.lastUpdatedAt != null && shippingAddress.lastUpdatedAt.isValid() ? shippingAddress.lastUpdatedAt.toJSON() : null
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
      res.body.forEach((shippingAddress: IShippingAddress) => {
        shippingAddress.createdAt = shippingAddress.createdAt != null ? moment(shippingAddress.createdAt) : null;
        shippingAddress.lastUpdatedAt = shippingAddress.lastUpdatedAt != null ? moment(shippingAddress.lastUpdatedAt) : null;
      });
    }
    return res;
  }
}
