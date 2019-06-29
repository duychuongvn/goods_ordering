import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IExchangeRate, ExchangeRate } from 'app/shared/model/exchange-rate.model';
import { ExchangeRateService } from './exchange-rate.service';

@Component({
  selector: 'jhi-exchange-rate-update',
  templateUrl: './exchange-rate-update.component.html'
})
export class ExchangeRateUpdateComponent implements OnInit {
  exchangeRate: IExchangeRate;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    rate: [],
    createdAt: [],
    lastUpdatedAt: [],
    createdBy: [],
    lastUpdatedBy: []
  });

  constructor(protected exchangeRateService: ExchangeRateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ exchangeRate }) => {
      this.updateForm(exchangeRate);
      this.exchangeRate = exchangeRate;
    });
  }

  updateForm(exchangeRate: IExchangeRate) {
    this.editForm.patchValue({
      id: exchangeRate.id,
      rate: exchangeRate.rate,
      createdAt: exchangeRate.createdAt != null ? exchangeRate.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedAt: exchangeRate.lastUpdatedAt != null ? exchangeRate.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: exchangeRate.createdBy,
      lastUpdatedBy: exchangeRate.lastUpdatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const exchangeRate = this.createFromForm();
    if (exchangeRate.id !== undefined) {
      this.subscribeToSaveResponse(this.exchangeRateService.update(exchangeRate));
    } else {
      this.subscribeToSaveResponse(this.exchangeRateService.create(exchangeRate));
    }
  }

  private createFromForm(): IExchangeRate {
    const entity = {
      ...new ExchangeRate(),
      id: this.editForm.get(['id']).value,
      rate: this.editForm.get(['rate']).value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExchangeRate>>) {
    result.subscribe((res: HttpResponse<IExchangeRate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
