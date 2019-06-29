import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPayment, Payment } from 'app/shared/model/payment.model';
import { PaymentService } from './payment.service';

@Component({
  selector: 'jhi-payment-update',
  templateUrl: './payment-update.component.html'
})
export class PaymentUpdateComponent implements OnInit {
  payment: IPayment;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    paymentCode: [],
    paymentStatus: [],
    receivedAmount: [],
    paidTime: [],
    bankAccount: [],
    bankAccountHolder: [],
    bankInfo: [],
    createdAt: [],
    lastUpdatedAt: [],
    createdBy: [],
    lastUpdatedBy: []
  });

  constructor(protected paymentService: PaymentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ payment }) => {
      this.updateForm(payment);
      this.payment = payment;
    });
  }

  updateForm(payment: IPayment) {
    this.editForm.patchValue({
      id: payment.id,
      paymentCode: payment.paymentCode,
      paymentStatus: payment.paymentStatus,
      receivedAmount: payment.receivedAmount,
      paidTime: payment.paidTime != null ? payment.paidTime.format(DATE_TIME_FORMAT) : null,
      bankAccount: payment.bankAccount,
      bankAccountHolder: payment.bankAccountHolder,
      bankInfo: payment.bankInfo,
      createdAt: payment.createdAt != null ? payment.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedAt: payment.lastUpdatedAt != null ? payment.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: payment.createdBy,
      lastUpdatedBy: payment.lastUpdatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const payment = this.createFromForm();
    if (payment.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  private createFromForm(): IPayment {
    const entity = {
      ...new Payment(),
      id: this.editForm.get(['id']).value,
      paymentCode: this.editForm.get(['paymentCode']).value,
      paymentStatus: this.editForm.get(['paymentStatus']).value,
      receivedAmount: this.editForm.get(['receivedAmount']).value,
      paidTime: this.editForm.get(['paidTime']).value != null ? moment(this.editForm.get(['paidTime']).value, DATE_TIME_FORMAT) : undefined,
      bankAccount: this.editForm.get(['bankAccount']).value,
      bankAccountHolder: this.editForm.get(['bankAccountHolder']).value,
      bankInfo: this.editForm.get(['bankInfo']).value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>) {
    result.subscribe((res: HttpResponse<IPayment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
