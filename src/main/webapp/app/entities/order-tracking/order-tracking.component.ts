import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrderTracking } from 'app/shared/model/order-tracking.model';
import { AccountService } from 'app/core';
import { OrderTrackingService } from './order-tracking.service';

@Component({
  selector: 'jhi-order-tracking',
  templateUrl: './order-tracking.component.html'
})
export class OrderTrackingComponent implements OnInit, OnDestroy {
  orderTrackings: IOrderTracking[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected orderTrackingService: OrderTrackingService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.orderTrackingService
      .query()
      .pipe(
        filter((res: HttpResponse<IOrderTracking[]>) => res.ok),
        map((res: HttpResponse<IOrderTracking[]>) => res.body)
      )
      .subscribe(
        (res: IOrderTracking[]) => {
          this.orderTrackings = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOrderTrackings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOrderTracking) {
    return item.id;
  }

  registerChangeInOrderTrackings() {
    this.eventSubscriber = this.eventManager.subscribe('orderTrackingListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
