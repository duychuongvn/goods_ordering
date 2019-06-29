import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOrderTracking, OrderTracking } from 'app/shared/model/order-tracking.model';
import { OrderTrackingService } from './order-tracking.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order';

@Component({
  selector: 'jhi-order-tracking-update',
  templateUrl: './order-tracking-update.component.html'
})
export class OrderTrackingUpdateComponent implements OnInit {
  orderTracking: IOrderTracking;
  isSaving: boolean;

  orders: IOrder[];

  editForm = this.fb.group({
    id: [],
    deliveryStatus: [],
    dateTime: [],
    createdAt: [],
    lastUpdatedAt: [],
    createdBy: [],
    lastUpdatedBy: [],
    order: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected orderTrackingService: OrderTrackingService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderTracking }) => {
      this.updateForm(orderTracking);
      this.orderTracking = orderTracking;
    });
    this.orderService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrder[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrder[]>) => response.body)
      )
      .subscribe((res: IOrder[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(orderTracking: IOrderTracking) {
    this.editForm.patchValue({
      id: orderTracking.id,
      deliveryStatus: orderTracking.deliveryStatus,
      dateTime: orderTracking.dateTime != null ? orderTracking.dateTime.format(DATE_TIME_FORMAT) : null,
      createdAt: orderTracking.createdAt != null ? orderTracking.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedAt: orderTracking.lastUpdatedAt != null ? orderTracking.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: orderTracking.createdBy,
      lastUpdatedBy: orderTracking.lastUpdatedBy,
      order: orderTracking.order
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orderTracking = this.createFromForm();
    if (orderTracking.id !== undefined) {
      this.subscribeToSaveResponse(this.orderTrackingService.update(orderTracking));
    } else {
      this.subscribeToSaveResponse(this.orderTrackingService.create(orderTracking));
    }
  }

  private createFromForm(): IOrderTracking {
    const entity = {
      ...new OrderTracking(),
      id: this.editForm.get(['id']).value,
      deliveryStatus: this.editForm.get(['deliveryStatus']).value,
      dateTime: this.editForm.get(['dateTime']).value != null ? moment(this.editForm.get(['dateTime']).value, DATE_TIME_FORMAT) : undefined,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      lastUpdatedAt:
        this.editForm.get(['lastUpdatedAt']).value != null
          ? moment(this.editForm.get(['lastUpdatedAt']).value, DATE_TIME_FORMAT)
          : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      lastUpdatedBy: this.editForm.get(['lastUpdatedBy']).value,
      order: this.editForm.get(['order']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderTracking>>) {
    result.subscribe((res: HttpResponse<IOrderTracking>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOrderById(index: number, item: IOrder) {
    return item.id;
  }
}
