import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ShippingAddress } from 'app/shared/model/shipping-address.model';
import { ShippingAddressService } from './shipping-address.service';
import { ShippingAddressComponent } from './shipping-address.component';
import { ShippingAddressDetailComponent } from './shipping-address-detail.component';
import { ShippingAddressUpdateComponent } from './shipping-address-update.component';
import { ShippingAddressDeletePopupComponent } from './shipping-address-delete-dialog.component';
import { IShippingAddress } from 'app/shared/model/shipping-address.model';

@Injectable({ providedIn: 'root' })
export class ShippingAddressResolve implements Resolve<IShippingAddress> {
  constructor(private service: ShippingAddressService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShippingAddress> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ShippingAddress>) => response.ok),
        map((shippingAddress: HttpResponse<ShippingAddress>) => shippingAddress.body)
      );
    }
    return of(new ShippingAddress());
  }
}

export const shippingAddressRoute: Routes = [
  {
    path: '',
    component: ShippingAddressComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.shippingAddress.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ShippingAddressDetailComponent,
    resolve: {
      shippingAddress: ShippingAddressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.shippingAddress.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ShippingAddressUpdateComponent,
    resolve: {
      shippingAddress: ShippingAddressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.shippingAddress.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ShippingAddressUpdateComponent,
    resolve: {
      shippingAddress: ShippingAddressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.shippingAddress.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const shippingAddressPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ShippingAddressDeletePopupComponent,
    resolve: {
      shippingAddress: ShippingAddressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodsorderApp.shippingAddress.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
