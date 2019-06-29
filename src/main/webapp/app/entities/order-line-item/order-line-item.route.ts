import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderLineItem } from 'app/shared/model/order-line-item.model';
import { OrderLineItemService } from './order-line-item.service';
import { OrderLineItemComponent } from './order-line-item.component';
import { OrderLineItemDetailComponent } from './order-line-item-detail.component';
import { OrderLineItemUpdateComponent } from './order-line-item-update.component';
import { OrderLineItemDeletePopupComponent } from './order-line-item-delete-dialog.component';
import { IOrderLineItem } from 'app/shared/model/order-line-item.model';

@Injectable({ providedIn: 'root' })
export class OrderLineItemResolve implements Resolve<IOrderLineItem> {
  constructor(private service: OrderLineItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderLineItem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrderLineItem>) => response.ok),
        map((orderLineItem: HttpResponse<OrderLineItem>) => orderLineItem.body)
      );
    }
    return of(new OrderLineItem());
  }
}

export const orderLineItemRoute: Routes = [
  {
    path: '',
    component: OrderLineItemComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderLineItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderLineItemDetailComponent,
    resolve: {
      orderLineItem: OrderLineItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderLineItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderLineItemUpdateComponent,
    resolve: {
      orderLineItem: OrderLineItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderLineItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderLineItemUpdateComponent,
    resolve: {
      orderLineItem: OrderLineItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderLineItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderLineItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrderLineItemDeletePopupComponent,
    resolve: {
      orderLineItem: OrderLineItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderLineItem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
