import { Moment } from 'moment';
import { IOrderLineItem } from 'app/shared/model/order-line-item.model';
import { IOrderTracking } from 'app/shared/model/order-tracking.model';
import { IUser } from 'app/core/user/user.model';

export const enum OrderStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  DEPOSITED = 'DEPOSITED',
  PAID = 'PAID'
}

export const enum DeliveryStatus {
  INIT = 'INIT',
  PICKED_UP = 'PICKED_UP',
  ON_BOARDING = 'ON_BOARDING',
  ARRIVED = 'ARRIVED',
  SENDING = 'SENDING',
  DONE = 'DONE'
}

export interface IOrder {
  id?: number;
  orderDate?: Moment;
  status?: OrderStatus;
  deliveryStatus?: DeliveryStatus;
  exchangeRateId?: string;
  remark?: string;
  createdAt?: Moment;
  lastUpdatedAt?: Moment;
  createdBy?: string;
  lastUpdatedBy?: string;
  orderLineItems?: IOrderLineItem[];
  orderTrackings?: IOrderTracking[];
  user?: IUser;
  merchant?: IUser;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderDate?: Moment,
    public status?: OrderStatus,
    public deliveryStatus?: DeliveryStatus,
    public exchangeRateId?: string,
    public remark?: string,
    public createdAt?: Moment,
    public lastUpdatedAt?: Moment,
    public createdBy?: string,
    public lastUpdatedBy?: string,
    public orderLineItems?: IOrderLineItem[],
    public orderTrackings?: IOrderTracking[],
    public user?: IUser,
    public merchant?: IUser
  ) {}
}
