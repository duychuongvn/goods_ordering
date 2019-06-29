import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IExchangeRate } from 'app/shared/model/exchange-rate.model';
import { AccountService } from 'app/core';
import { ExchangeRateService } from './exchange-rate.service';

@Component({
  selector: 'jhi-exchange-rate',
  templateUrl: './exchange-rate.component.html'
})
export class ExchangeRateComponent implements OnInit, OnDestroy {
  exchangeRates: IExchangeRate[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected exchangeRateService: ExchangeRateService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.exchangeRateService
      .query()
      .pipe(
        filter((res: HttpResponse<IExchangeRate[]>) => res.ok),
        map((res: HttpResponse<IExchangeRate[]>) => res.body)
      )
      .subscribe(
        (res: IExchangeRate[]) => {
          this.exchangeRates = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInExchangeRates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IExchangeRate) {
    return item.id;
  }

  registerChangeInExchangeRates() {
    this.eventSubscriber = this.eventManager.subscribe('exchangeRateListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
