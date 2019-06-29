import { Moment } from 'moment';
import { IOrder } from 'app/shared/model/order.model';
import { ITransaction } from 'app/shared/model/transaction.model';

export const enum PaymentStatus {
  PENDING = 'PENDING',
  PART_RECEIVED = 'PART_RECEIVED',
  FULL_RECEIVED = 'FULL_RECEIVED',
  REFUNDED = 'REFUNDED'
}

export interface IPayment {
  id?: number;
  paymentCode?: string;
  paymentStatus?: PaymentStatus;
  receivedAmount?: number;
  paidTime?: Moment;
  bankAccount?: string;
  bankAccountHolder?: string;
  bankInfo?: string;
  createdAt?: Moment;
  lastUpdatedAt?: Moment;
  createdBy?: string;
  lastUpdatedBy?: string;
  order?: IOrder;
  transactions?: ITransaction[];
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public paymentCode?: string,
    public paymentStatus?: PaymentStatus,
    public receivedAmount?: number,
    public paidTime?: Moment,
    public bankAccount?: string,
    public bankAccountHolder?: string,
    public bankInfo?: string,
    public createdAt?: Moment,
    public lastUpdatedAt?: Moment,
    public createdBy?: string,
    public lastUpdatedBy?: string,
    public order?: IOrder,
    public transactions?: ITransaction[]
  ) {}
}
