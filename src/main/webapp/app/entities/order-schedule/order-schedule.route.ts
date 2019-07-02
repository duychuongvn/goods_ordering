import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderSchedule } from 'app/shared/model/order-schedule.model';
import { OrderScheduleService } from './order-schedule.service';
import { OrderScheduleComponent } from './order-schedule.component';
import { OrderScheduleDetailComponent } from './order-schedule-detail.component';
import { OrderScheduleUpdateComponent } from './order-schedule-update.component';
import { OrderScheduleDeletePopupComponent } from './order-schedule-delete-dialog.component';
import { IOrderSchedule } from 'app/shared/model/order-schedule.model';

@Injectable({ providedIn: 'root' })
export class OrderScheduleResolve implements Resolve<IOrderSchedule> {
  constructor(private service: OrderScheduleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderSchedule> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrderSchedule>) => response.ok),
        map((orderSchedule: HttpResponse<OrderSchedule>) => orderSchedule.body)
      );
    }
    return of(new OrderSchedule());
  }
}

export const orderScheduleRoute: Routes = [
  {
    path: '',
    component: OrderScheduleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderScheduleDetailComponent,
    resolve: {
      orderSchedule: OrderScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderScheduleUpdateComponent,
    resolve: {
      orderSchedule: OrderScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderScheduleUpdateComponent,
    resolve: {
      orderSchedule: OrderScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderSchedulePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrderScheduleDeletePopupComponent,
    resolve: {
      orderSchedule: OrderScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.orderSchedule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
