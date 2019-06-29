import { IOrder } from 'app/shared/model/order.model';

export const enum OrderSource {
  AEO_JP = 'AEO_JP',
  ABC_MART = 'ABC_MART'
}

export interface IOrderLineItem {
  id?: number;
  referenceUrl?: string;
  originPrice?: number;
  salePrice?: number;
  tax?: number;
  goodsName?: string;
  goodsId?: string;
  goodsSKU?: string;
  size?: string;
  remark?: string;
  imagesContentType?: string;
  images?: any;
  source?: OrderSource;
  order?: IOrder;
}

export class OrderLineItem implements IOrderLineItem {
  constructor(
    public id?: number,
    public referenceUrl?: string,
    public originPrice?: number,
    public salePrice?: number,
    public tax?: number,
    public goodsName?: string,
    public goodsId?: string,
    public goodsSKU?: string,
    public size?: string,
    public remark?: string,
    public imagesContentType?: string,
    public images?: any,
    public source?: OrderSource,
    public order?: IOrder
  ) {}
}
