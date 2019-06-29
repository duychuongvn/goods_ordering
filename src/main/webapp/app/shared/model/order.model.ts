import { Moment } from 'moment';
import { IPayment } from 'app/shared/model/payment.model';
import { IOrderLineItem } from 'app/shared/model/order-line-item.model';
import { IOrderTracking } from 'app/shared/model/order-tracking.model';
import { IUser } from 'app/core/user/user.model';

export const enum OrderStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  DEPOSITED = 'DEPOSITED',
  PAID = 'PAID',
  CANCELED = 'CANCELED'
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
  paymentCode?: string;
  orderDate?: Moment;
  status?: OrderStatus;
  deliveryStatus?: DeliveryStatus;
  exchangeRateId?: string;
  exchangeRate?: number;
  totalJpyPrice?: number;
  deliveryFeeVnd?: number;
  totalPayVnd?: number;
  depositedVnd?: number;
  paidVnd?: number;
  packingDate?: Moment;
  estimatedDeliverDate?: Moment;
  deliveredDate?: Moment;
  finishPaymentTime?: Moment;
  remark?: string;
  address1?: string;
  address2?: string;
  phone1?: string;
  phone2?: string;
  email?: string;
  zipCode?: string;
  city?: string;
  district?: string;
  createdAt?: Moment;
  lastUpdatedAt?: Moment;
  createdBy?: string;
  lastUpdatedBy?: string;
  payment?: IPayment;
  orderLineItems?: IOrderLineItem[];
  orderTrackings?: IOrderTracking[];
  user?: IUser;
  merchant?: IUser;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public paymentCode?: string,
    public orderDate?: Moment,
    public status?: OrderStatus,
    public deliveryStatus?: DeliveryStatus,
    public exchangeRateId?: string,
    public exchangeRate?: number,
    public totalJpyPrice?: number,
    public deliveryFeeVnd?: number,
    public totalPayVnd?: number,
    public depositedVnd?: number,
    public paidVnd?: number,
    public packingDate?: Moment,
    public estimatedDeliverDate?: Moment,
    public deliveredDate?: Moment,
    public finishPaymentTime?: Moment,
    public remark?: string,
    public address1?: string,
    public address2?: string,
    public phone1?: string,
    public phone2?: string,
    public email?: string,
    public zipCode?: string,
    public city?: string,
    public district?: string,
    public createdAt?: Moment,
    public lastUpdatedAt?: Moment,
    public createdBy?: string,
    public lastUpdatedBy?: string,
    public payment?: IPayment,
    public orderLineItems?: IOrderLineItem[],
    public orderTrackings?: IOrderTracking[],
    public user?: IUser,
    public merchant?: IUser
  ) {}
}
