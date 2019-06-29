import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'order',
        loadChildren: './order/order.module#GoodsorderOrderModule'
      },
      {
        path: 'order-tracking',
        loadChildren: './order-tracking/order-tracking.module#GoodsorderOrderTrackingModule'
      },
      {
        path: 'order-line-item',
        loadChildren: './order-line-item/order-line-item.module#GoodsorderOrderLineItemModule'
      },
      {
        path: 'shipping-address',
        loadChildren: './shipping-address/shipping-address.module#GoodsorderShippingAddressModule'
      },
      {
        path: 'order',
        loadChildren: './order/order.module#GoodsorderOrderModule'
      },
      {
        path: 'order-tracking',
        loadChildren: './order-tracking/order-tracking.module#GoodsorderOrderTrackingModule'
      },
      {
        path: 'order-line-item',
        loadChildren: './order-line-item/order-line-item.module#GoodsorderOrderLineItemModule'
      },
      {
        path: 'exchange-rate',
        loadChildren: './exchange-rate/exchange-rate.module#GoodsorderExchangeRateModule'
      },
      {
        path: 'order',
        loadChildren: './order/order.module#GoodsorderOrderModule'
      },
      {
        path: 'order-tracking',
        loadChildren: './order-tracking/order-tracking.module#GoodsorderOrderTrackingModule'
      },
      {
        path: 'order-line-item',
        loadChildren: './order-line-item/order-line-item.module#GoodsorderOrderLineItemModule'
      },
      {
        path: 'order',
        loadChildren: './order/order.module#GoodsorderOrderModule'
      },
      {
        path: 'order-tracking',
        loadChildren: './order-tracking/order-tracking.module#GoodsorderOrderTrackingModule'
      },
      {
        path: 'order-line-item',
        loadChildren: './order-line-item/order-line-item.module#GoodsorderOrderLineItemModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GoodsorderEntityModule {}
