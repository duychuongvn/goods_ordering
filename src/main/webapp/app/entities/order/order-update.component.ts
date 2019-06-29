import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  order: IOrder;
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    orderDate: [],
    status: [null, [Validators.required]],
    deliveryStatus: [],
    exchangeRateId: [null, [Validators.required]],
    remark: [],
    createdAt: [],
    lastUpdatedAt: [],
    createdBy: [],
    lastUpdatedBy: [],
    user: [],
    merchant: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected orderService: OrderService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ order }) => {
      this.updateForm(order);
      this.order = order;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(order: IOrder) {
    this.editForm.patchValue({
      id: order.id,
      orderDate: order.orderDate != null ? order.orderDate.format(DATE_TIME_FORMAT) : null,
      status: order.status,
      deliveryStatus: order.deliveryStatus,
      exchangeRateId: order.exchangeRateId,
      remark: order.remark,
      createdAt: order.createdAt != null ? order.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedAt: order.lastUpdatedAt != null ? order.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: order.createdBy,
      lastUpdatedBy: order.lastUpdatedBy,
      user: order.user,
      merchant: order.merchant
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    const entity = {
      ...new Order(),
      id: this.editForm.get(['id']).value,
      orderDate:
        this.editForm.get(['orderDate']).value != null ? moment(this.editForm.get(['orderDate']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value,
      deliveryStatus: this.editForm.get(['deliveryStatus']).value,
      exchangeRateId: this.editForm.get(['exchangeRateId']).value,
      remark: this.editForm.get(['remark']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      lastUpdatedAt:
        this.editForm.get(['lastUpdatedAt']).value != null
          ? moment(this.editForm.get(['lastUpdatedAt']).value, DATE_TIME_FORMAT)
          : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      lastUpdatedBy: this.editForm.get(['lastUpdatedBy']).value,
      user: this.editForm.get(['user']).value,
      merchant: this.editForm.get(['merchant']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>) {
    result.subscribe((res: HttpResponse<IOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
