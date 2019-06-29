import { Moment } from 'moment';

export interface IBankAccount {
  id?: number;
  account?: string;
  fullName?: string;
  bankInfo?: string;
  createdAt?: Moment;
  lastUpdatedAt?: Moment;
  createdBy?: string;
  lastUpdatedBy?: string;
}

export class BankAccount implements IBankAccount {
  constructor(
    public id?: number,
    public account?: string,
    public fullName?: string,
    public bankInfo?: string,
    public createdAt?: Moment,
    public lastUpdatedAt?: Moment,
    public createdBy?: string,
    public lastUpdatedBy?: string
  ) {}
}
