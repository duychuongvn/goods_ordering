import { Moment } from 'moment';

export interface IExchangeRate {
  id?: number;
  rate?: number;
  createdAt?: Moment;
  lastUpdatedAt?: Moment;
  createdBy?: string;
  lastUpdatedBy?: string;
}

export class ExchangeRate implements IExchangeRate {
  constructor(
    public id?: number,
    public rate?: number,
    public createdAt?: Moment,
    public lastUpdatedAt?: Moment,
    public createdBy?: string,
    public lastUpdatedBy?: string
  ) {}
}
