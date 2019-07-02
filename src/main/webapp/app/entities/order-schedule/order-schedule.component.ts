import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrderSchedule } from 'app/shared/model/order-schedule.model';
import { AccountService } from 'app/core';
import { OrderScheduleService } from './order-schedule.service';

@Component({
  selector: 'jhi-order-schedule',
  templateUrl: './order-schedule.component.html'
})
export class OrderScheduleComponent implements OnInit, OnDestroy {
  orderSchedules: IOrderSchedule[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected orderScheduleService: OrderScheduleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.orderScheduleService
      .query()
      .pipe(
        filter((res: HttpResponse<IOrderSchedule[]>) => res.ok),
        map((res: HttpResponse<IOrderSchedule[]>) => res.body)
      )
      .subscribe(
        (res: IOrderSchedule[]) => {
          this.orderSchedules = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOrderSchedules();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOrderSchedule) {
    return item.id;
  }

  registerChangeInOrderSchedules() {
    this.eventSubscriber = this.eventManager.subscribe('orderScheduleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
