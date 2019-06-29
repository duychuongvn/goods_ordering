import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExchangeRate } from 'app/shared/model/exchange-rate.model';
import { ExchangeRateService } from './exchange-rate.service';
import { ExchangeRateComponent } from './exchange-rate.component';
import { ExchangeRateDetailComponent } from './exchange-rate-detail.component';
import { ExchangeRateUpdateComponent } from './exchange-rate-update.component';
import { ExchangeRateDeletePopupComponent } from './exchange-rate-delete-dialog.component';
import { IExchangeRate } from 'app/shared/model/exchange-rate.model';

@Injectable({ providedIn: 'root' })
export class ExchangeRateResolve implements Resolve<IExchangeRate> {
  constructor(private service: ExchangeRateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExchangeRate> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ExchangeRate>) => response.ok),
        map((exchangeRate: HttpResponse<ExchangeRate>) => exchangeRate.body)
      );
    }
    return of(new ExchangeRate());
  }
}

export const exchangeRateRoute: Routes = [
  {
    path: '',
    component: ExchangeRateComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.exchangeRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExchangeRateDetailComponent,
    resolve: {
      exchangeRate: ExchangeRateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.exchangeRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExchangeRateUpdateComponent,
    resolve: {
      exchangeRate: ExchangeRateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.exchangeRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExchangeRateUpdateComponent,
    resolve: {
      exchangeRate: ExchangeRateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.exchangeRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const exchangeRatePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ExchangeRateDeletePopupComponent,
    resolve: {
      exchangeRate: ExchangeRateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.exchangeRate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
