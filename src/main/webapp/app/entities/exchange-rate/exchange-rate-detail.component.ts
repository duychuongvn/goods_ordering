import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExchangeRate } from 'app/shared/model/exchange-rate.model';

@Component({
  selector: 'jhi-exchange-rate-detail',
  templateUrl: './exchange-rate-detail.component.html'
})
export class ExchangeRateDetailComponent implements OnInit {
  exchangeRate: IExchangeRate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ exchangeRate }) => {
      this.exchangeRate = exchangeRate;
    });
  }

  previousState() {
    window.history.back();
  }
}
