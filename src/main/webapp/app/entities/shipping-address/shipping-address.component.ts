import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IShippingAddress } from 'app/shared/model/shipping-address.model';
import { AccountService } from 'app/core';
import { ShippingAddressService } from './shipping-address.service';

@Component({
  selector: 'jhi-shipping-address',
  templateUrl: './shipping-address.component.html'
})
export class ShippingAddressComponent implements OnInit, OnDestroy {
  shippingAddresses: IShippingAddress[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected shippingAddressService: ShippingAddressService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.shippingAddressService
      .query()
      .pipe(
        filter((res: HttpResponse<IShippingAddress[]>) => res.ok),
        map((res: HttpResponse<IShippingAddress[]>) => res.body)
      )
      .subscribe(
        (res: IShippingAddress[]) => {
          this.shippingAddresses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInShippingAddresses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShippingAddress) {
    return item.id;
  }

  registerChangeInShippingAddresses() {
    this.eventSubscriber = this.eventManager.subscribe('shippingAddressListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
