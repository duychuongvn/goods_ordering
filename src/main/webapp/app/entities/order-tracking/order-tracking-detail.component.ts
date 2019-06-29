import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderTracking } from 'app/shared/model/order-tracking.model';

@Component({
  selector: 'jhi-order-tracking-detail',
  templateUrl: './order-tracking-detail.component.html'
})
export class OrderTrackingDetailComponent implements OnInit {
  orderTracking: IOrderTracking;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderTracking }) => {
      this.orderTracking = orderTracking;
    });
  }

  previousState() {
    window.history.back();
  }
}
