import { Moment } from 'moment';

export const enum OrderScheduleStatus {
  OPEN = 'OPEN',
  CLOSED = 'CLOSED'
}

export interface IOrderSchedule {
  id?: number;
  openDate?: Moment;
  closeDate?: Moment;
  expectedPackingDate?: Moment;
  expectedDeliveryDate?: Moment;
  maxOrderNumber?: number;
  currentOrderNumber?: number;
  status?: OrderScheduleStatus;
  createdAt?: Moment;
  lastUpdatedAt?: Moment;
  createdBy?: string;
  lastUpdatedBy?: string;
}

export class OrderSchedule implements IOrderSchedule {
  constructor(
    public id?: number,
    public openDate?: Moment,
    public closeDate?: Moment,
    public expectedPackingDate?: Moment,
    public expectedDeliveryDate?: Moment,
    public maxOrderNumber?: number,
    public currentOrderNumber?: number,
    public status?: OrderScheduleStatus,
    public createdAt?: Moment,
    public lastUpdatedAt?: Moment,
    public createdBy?: string,
    public lastUpdatedBy?: string
  ) {}
}
