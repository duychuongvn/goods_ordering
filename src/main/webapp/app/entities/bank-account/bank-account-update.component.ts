import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IBankAccount, BankAccount } from 'app/shared/model/bank-account.model';
import { BankAccountService } from './bank-account.service';

@Component({
  selector: 'jhi-bank-account-update',
  templateUrl: './bank-account-update.component.html'
})
export class BankAccountUpdateComponent implements OnInit {
  bankAccount: IBankAccount;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    account: [],
    fullName: [],
    bankInfo: [],
    createdAt: [],
    lastUpdatedAt: [],
    createdBy: [],
    lastUpdatedBy: []
  });

  constructor(protected bankAccountService: BankAccountService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bankAccount }) => {
      this.updateForm(bankAccount);
      this.bankAccount = bankAccount;
    });
  }

  updateForm(bankAccount: IBankAccount) {
    this.editForm.patchValue({
      id: bankAccount.id,
      account: bankAccount.account,
      fullName: bankAccount.fullName,
      bankInfo: bankAccount.bankInfo,
      createdAt: bankAccount.createdAt != null ? bankAccount.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedAt: bankAccount.lastUpdatedAt != null ? bankAccount.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: bankAccount.createdBy,
      lastUpdatedBy: bankAccount.lastUpdatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bankAccount = this.createFromForm();
    if (bankAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.bankAccountService.update(bankAccount));
    } else {
      this.subscribeToSaveResponse(this.bankAccountService.create(bankAccount));
    }
  }

  private createFromForm(): IBankAccount {
    const entity = {
      ...new BankAccount(),
      id: this.editForm.get(['id']).value,
      account: this.editForm.get(['account']).value,
      fullName: this.editForm.get(['fullName']).value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankAccount>>) {
    result.subscribe((res: HttpResponse<IBankAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
