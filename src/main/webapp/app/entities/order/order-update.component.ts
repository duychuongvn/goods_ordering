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
import { IPayment } from 'app/shared/model/payment.model';
import { PaymentService } from 'app/entities/payment';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  order: IOrder;
  isSaving: boolean;

  payments: IPayment[];

  users: IUser[];
  packingDateDp: any;
  estimatedDeliverDateDp: any;
  deliveredDateDp: any;

  editForm = this.fb.group({
    id: [],
    paymentCode: [null, [Validators.required]],
    orderDate: [],
    status: [null, [Validators.required]],
    deliveryStatus: [],
    exchangeRateId: [null, [Validators.required]],
    exchangeRate: [],
    totalJpyPrice: [],
    deliveryFeeVnd: [],
    totalPayVnd: [],
    depositedVnd: [],
    paidVnd: [],
    packingDate: [],
    estimatedDeliverDate: [],
    deliveredDate: [],
    finishPaymentTime: [],
    remark: [],
    address1: [],
    address2: [],
    phone1: [],
    phone2: [],
    email: [],
    zipCode: [],
    city: [],
    district: [],
    createdAt: [],
    lastUpdatedAt: [],
    createdBy: [],
    lastUpdatedBy: [],
    payment: [],
    user: [],
    merchant: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected orderService: OrderService,
    protected paymentService: PaymentService,
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
    this.paymentService
      .query({ filter: 'order-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPayment[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPayment[]>) => response.body)
      )
      .subscribe(
        (res: IPayment[]) => {
          if (!this.order.payment || !this.order.payment.id) {
            this.payments = res;
          } else {
            this.paymentService
              .find(this.order.payment.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPayment>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPayment>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPayment) => (this.payments = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
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
      paymentCode: order.paymentCode,
      orderDate: order.orderDate != null ? order.orderDate.format(DATE_TIME_FORMAT) : null,
      status: order.status,
      deliveryStatus: order.deliveryStatus,
      exchangeRateId: order.exchangeRateId,
      exchangeRate: order.exchangeRate,
      totalJpyPrice: order.totalJpyPrice,
      deliveryFeeVnd: order.deliveryFeeVnd,
      totalPayVnd: order.totalPayVnd,
      depositedVnd: order.depositedVnd,
      paidVnd: order.paidVnd,
      packingDate: order.packingDate,
      estimatedDeliverDate: order.estimatedDeliverDate,
      deliveredDate: order.deliveredDate,
      finishPaymentTime: order.finishPaymentTime != null ? order.finishPaymentTime.format(DATE_TIME_FORMAT) : null,
      remark: order.remark,
      address1: order.address1,
      address2: order.address2,
      phone1: order.phone1,
      phone2: order.phone2,
      email: order.email,
      zipCode: order.zipCode,
      city: order.city,
      district: order.district,
      createdAt: order.createdAt != null ? order.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedAt: order.lastUpdatedAt != null ? order.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: order.createdBy,
      lastUpdatedBy: order.lastUpdatedBy,
      payment: order.payment,
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
      paymentCode: this.editForm.get(['paymentCode']).value,
      orderDate:
        this.editForm.get(['orderDate']).value != null ? moment(this.editForm.get(['orderDate']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value,
      deliveryStatus: this.editForm.get(['deliveryStatus']).value,
      exchangeRateId: this.editForm.get(['exchangeRateId']).value,
      exchangeRate: this.editForm.get(['exchangeRate']).value,
      totalJpyPrice: this.editForm.get(['totalJpyPrice']).value,
      deliveryFeeVnd: this.editForm.get(['deliveryFeeVnd']).value,
      totalPayVnd: this.editForm.get(['totalPayVnd']).value,
      depositedVnd: this.editForm.get(['depositedVnd']).value,
      paidVnd: this.editForm.get(['paidVnd']).value,
      packingDate: this.editForm.get(['packingDate']).value,
      estimatedDeliverDate: this.editForm.get(['estimatedDeliverDate']).value,
      deliveredDate: this.editForm.get(['deliveredDate']).value,
      finishPaymentTime:
        this.editForm.get(['finishPaymentTime']).value != null
          ? moment(this.editForm.get(['finishPaymentTime']).value, DATE_TIME_FORMAT)
          : undefined,
      remark: this.editForm.get(['remark']).value,
      address1: this.editForm.get(['address1']).value,
      address2: this.editForm.get(['address2']).value,
      phone1: this.editForm.get(['phone1']).value,
      phone2: this.editForm.get(['phone2']).value,
      email: this.editForm.get(['email']).value,
      zipCode: this.editForm.get(['zipCode']).value,
      city: this.editForm.get(['city']).value,
      district: this.editForm.get(['district']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      lastUpdatedAt:
        this.editForm.get(['lastUpdatedAt']).value != null
          ? moment(this.editForm.get(['lastUpdatedAt']).value, DATE_TIME_FORMAT)
          : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      lastUpdatedBy: this.editForm.get(['lastUpdatedBy']).value,
      payment: this.editForm.get(['payment']).value,
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

  trackPaymentById(index: number, item: IPayment) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
