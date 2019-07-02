import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IOrderSchedule, OrderSchedule } from 'app/shared/model/order-schedule.model';
import { OrderScheduleService } from './order-schedule.service';

@Component({
  selector: 'jhi-order-schedule-update',
  templateUrl: './order-schedule-update.component.html'
})
export class OrderScheduleUpdateComponent implements OnInit {
  orderSchedule: IOrderSchedule;
  isSaving: boolean;
  openDateDp: any;
  closeDateDp: any;
  expectedPackingDateDp: any;
  expectedDeliveryDateDp: any;

  editForm = this.fb.group({
    id: [],
    openDate: [],
    closeDate: [],
    expectedPackingDate: [],
    expectedDeliveryDate: [],
    maxOrderNumber: [],
    currentOrderNumber: [],
    status: [],
    createdAt: [],
    lastUpdatedAt: [],
    createdBy: [],
    lastUpdatedBy: []
  });

  constructor(protected orderScheduleService: OrderScheduleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderSchedule }) => {
      this.updateForm(orderSchedule);
      this.orderSchedule = orderSchedule;
    });
  }

  updateForm(orderSchedule: IOrderSchedule) {
    this.editForm.patchValue({
      id: orderSchedule.id,
      openDate: orderSchedule.openDate,
      closeDate: orderSchedule.closeDate,
      expectedPackingDate: orderSchedule.expectedPackingDate,
      expectedDeliveryDate: orderSchedule.expectedDeliveryDate,
      maxOrderNumber: orderSchedule.maxOrderNumber,
      currentOrderNumber: orderSchedule.currentOrderNumber,
      status: orderSchedule.status,
      createdAt: orderSchedule.createdAt != null ? orderSchedule.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedAt: orderSchedule.lastUpdatedAt != null ? orderSchedule.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: orderSchedule.createdBy,
      lastUpdatedBy: orderSchedule.lastUpdatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orderSchedule = this.createFromForm();
    if (orderSchedule.id !== undefined) {
      this.subscribeToSaveResponse(this.orderScheduleService.update(orderSchedule));
    } else {
      this.subscribeToSaveResponse(this.orderScheduleService.create(orderSchedule));
    }
  }

  private createFromForm(): IOrderSchedule {
    const entity = {
      ...new OrderSchedule(),
      id: this.editForm.get(['id']).value,
      openDate: this.editForm.get(['openDate']).value,
      closeDate: this.editForm.get(['closeDate']).value,
      expectedPackingDate: this.editForm.get(['expectedPackingDate']).value,
      expectedDeliveryDate: this.editForm.get(['expectedDeliveryDate']).value,
      maxOrderNumber: this.editForm.get(['maxOrderNumber']).value,
      currentOrderNumber: this.editForm.get(['currentOrderNumber']).value,
      status: this.editForm.get(['status']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      lastUpdatedAt:
        this.editForm.get(['lastUpdatedAt']).value != null
          ? moment(this.editForm.get(['lastUpdatedAt']).value, DATE_TIME_FORMAT)
          : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      lastUpdatedBy: this.editForm.get(['lastUpdatedBy']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderSchedule>>) {
    result.subscribe((res: HttpResponse<IOrderSchedule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
