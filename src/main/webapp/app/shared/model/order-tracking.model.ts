import { Moment } from 'moment';
import { IOrder } from 'app/shared/model/order.model';

export const enum DeliveryStatus {
  INIT = 'INIT',
  PICKED_UP = 'PICKED_UP',
  ON_BOARDING = 'ON_BOARDING',
  ARRIVED = 'ARRIVED',
  SENDING = 'SENDING',
  DONE = 'DONE'
}

export interface IOrderTracking {
  id?: number;
  deliveryStatus?: DeliveryStatus;
  dateTime?: Moment;
  remark?: string;
  createdAt?: Moment;
  lastUpdatedAt?: Moment;
  createdBy?: string;
  lastUpdatedBy?: string;
  order?: IOrder;
}

export class OrderTracking implements IOrderTracking {
  constructor(
    public id?: number,
    public deliveryStatus?: DeliveryStatus,
    public dateTime?: Moment,
    public remark?: string,
    public createdAt?: Moment,
    public lastUpdatedAt?: Moment,
    public createdBy?: string,
    public lastUpdatedBy?: string,
    public order?: IOrder
  ) {}
}
