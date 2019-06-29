import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOrderLineItem } from 'app/shared/model/order-line-item.model';

@Component({
  selector: 'jhi-order-line-item-detail',
  templateUrl: './order-line-item-detail.component.html'
})
export class OrderLineItemDetailComponent implements OnInit {
  orderLineItem: IOrderLineItem;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderLineItem }) => {
      this.orderLineItem = orderLineItem;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
