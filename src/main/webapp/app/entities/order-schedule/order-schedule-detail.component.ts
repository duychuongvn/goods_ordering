import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderSchedule } from 'app/shared/model/order-schedule.model';

@Component({
  selector: 'jhi-order-schedule-detail',
  templateUrl: './order-schedule-detail.component.html'
})
export class OrderScheduleDetailComponent implements OnInit {
  orderSchedule: IOrderSchedule;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderSchedule }) => {
      this.orderSchedule = orderSchedule;
    });
  }

  previousState() {
    window.history.back();
  }
}
