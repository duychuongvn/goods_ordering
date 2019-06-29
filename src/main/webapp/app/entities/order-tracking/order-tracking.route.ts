import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderTracking } from 'app/shared/model/order-tracking.model';
import { OrderTrackingService } from './order-tracking.service';
import { OrderTrackingComponent } from './order-tracking.component';
import { OrderTrackingDetailComponent } from './order-tracking-detail.component';
import { OrderTrackingUpdateComponent } from './order-tracking-update.component';
import { OrderTrackingDeletePopupComponent } from './order-tracking-delete-dialog.component';
import { IOrderTracking } from 'app/shared/model/order-tracking.model';

@Injectable({ providedIn: 'root' })
export class OrderTrackingResolve implements Resolve<IOrderTracking> {
  constructor(private service: OrderTrackingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderTracking> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrderTracking>) => response.ok),
        map((orderTracking: HttpResponse<OrderTracking>) => orderTracking.body)
      );
    }
    return of(new OrderTracking());
  }
}

export const orderTrackingRoute: Routes = [
  {
    path: '',
    component: OrderTrackingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderTrackingDetailComponent,
    resolve: {
      orderTracking: OrderTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderTrackingUpdateComponent,
    resolve: {
      orderTracking: OrderTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderTrackingUpdateComponent,
    resolve: {
      orderTracking: OrderTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderTrackingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrderTrackingDeletePopupComponent,
    resolve: {
      orderTracking: OrderTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderTracking.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
