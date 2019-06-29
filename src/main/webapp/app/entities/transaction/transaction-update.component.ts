import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { IPayment } from 'app/shared/model/payment.model';
import { PaymentService } from 'app/entities/payment';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html'
})
export class TransactionUpdateComponent implements OnInit {
  transaction: ITransaction;
  isSaving: boolean;

  payments: IPayment[];

  editForm = this.fb.group({
    id: [],
    transactionType: [],
    transactionDate: [],
    amount: [],
    paymentMethod: [],
    createdAt: [],
    lastUpdatedAt: [],
    createdBy: [],
    lastUpdatedBy: [],
    payment: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transactionService: TransactionService,
    protected paymentService: PaymentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);
      this.transaction = transaction;
    });
    this.paymentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPayment[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPayment[]>) => response.body)
      )
      .subscribe((res: IPayment[]) => (this.payments = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transaction: ITransaction) {
    this.editForm.patchValue({
      id: transaction.id,
      transactionType: transaction.transactionType,
      transactionDate: transaction.transactionDate != null ? transaction.transactionDate.format(DATE_TIME_FORMAT) : null,
      amount: transaction.amount,
      paymentMethod: transaction.paymentMethod,
      createdAt: transaction.createdAt != null ? transaction.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedAt: transaction.lastUpdatedAt != null ? transaction.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: transaction.createdBy,
      lastUpdatedBy: transaction.lastUpdatedBy,
      payment: transaction.payment
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    const entity = {
      ...new Transaction(),
      id: this.editForm.get(['id']).value,
      transactionType: this.editForm.get(['transactionType']).value,
      transactionDate:
        this.editForm.get(['transactionDate']).value != null
          ? moment(this.editForm.get(['transactionDate']).value, DATE_TIME_FORMAT)
          : undefined,
      amount: this.editForm.get(['amount']).value,
      paymentMethod: this.editForm.get(['paymentMethod']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      lastUpdatedAt:
        this.editForm.get(['lastUpdatedAt']).value != null
          ? moment(this.editForm.get(['lastUpdatedAt']).value, DATE_TIME_FORMAT)
          : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      lastUpdatedBy: this.editForm.get(['lastUpdatedBy']).value,
      payment: this.editForm.get(['payment']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>) {
    result.subscribe((res: HttpResponse<ITransaction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
