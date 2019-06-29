import { Moment } from 'moment';
import { IPayment } from 'app/shared/model/payment.model';

export const enum TransactionType {
  DEPOSIT = 'DEPOSIT',
  PARTIAL = 'PARTIAL',
  FULL = 'FULL'
}

export const enum PaymentMethod {
  BANK_TRANSFER = 'BANK_TRANSFER',
  CASH = 'CASH'
}

export interface ITransaction {
  id?: number;
  transactionType?: TransactionType;
  transactionDate?: Moment;
  amount?: number;
  paymentMethod?: PaymentMethod;
  createdAt?: Moment;
  lastUpdatedAt?: Moment;
  createdBy?: string;
  lastUpdatedBy?: string;
  payment?: IPayment;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public transactionType?: TransactionType,
    public transactionDate?: Moment,
    public amount?: number,
    public paymentMethod?: PaymentMethod,
    public createdAt?: Moment,
    public lastUpdatedAt?: Moment,
    public createdBy?: string,
    public lastUpdatedBy?: string,
    public payment?: IPayment
  ) {}
}
