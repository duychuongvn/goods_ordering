import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IShippingAddress {
  id?: number;
  address1?: string;
  address2?: string;
  phone1?: string;
  phone2?: string;
  email1?: string;
  email2?: string;
  zipCode?: string;
  city?: string;
  district?: string;
  defaultAddress?: boolean;
  createdAt?: Moment;
  lastUpdatedAt?: Moment;
  createdBy?: string;
  lastUpdatedBy?: string;
  user?: IUser;
}

export class ShippingAddress implements IShippingAddress {
  constructor(
    public id?: number,
    public address1?: string,
    public address2?: string,
    public phone1?: string,
    public phone2?: string,
    public email1?: string,
    public email2?: string,
    public zipCode?: string,
    public city?: string,
    public district?: string,
    public defaultAddress?: boolean,
    public createdAt?: Moment,
    public lastUpdatedAt?: Moment,
    public createdBy?: string,
    public lastUpdatedBy?: string,
    public user?: IUser
  ) {
    this.defaultAddress = this.defaultAddress || false;
  }
}
