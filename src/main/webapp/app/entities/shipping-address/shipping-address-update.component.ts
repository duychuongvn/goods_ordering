import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IShippingAddress, ShippingAddress } from 'app/shared/model/shipping-address.model';
import { ShippingAddressService } from './shipping-address.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-shipping-address-update',
  templateUrl: './shipping-address-update.component.html'
})
export class ShippingAddressUpdateComponent implements OnInit {
  shippingAddress: IShippingAddress;
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    address1: [],
    address2: [],
    phone1: [],
    phone2: [],
    email1: [],
    email2: [],
    zipCode: [],
    city: [],
    district: [],
    defaultAddress: [],
    createdAt: [],
    lastUpdatedAt: [],
    createdBy: [],
    lastUpdatedBy: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected shippingAddressService: ShippingAddressService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ shippingAddress }) => {
      this.updateForm(shippingAddress);
      this.shippingAddress = shippingAddress;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(shippingAddress: IShippingAddress) {
    this.editForm.patchValue({
      id: shippingAddress.id,
      address1: shippingAddress.address1,
      address2: shippingAddress.address2,
      phone1: shippingAddress.phone1,
      phone2: shippingAddress.phone2,
      email1: shippingAddress.email1,
      email2: shippingAddress.email2,
      zipCode: shippingAddress.zipCode,
      city: shippingAddress.city,
      district: shippingAddress.district,
      defaultAddress: shippingAddress.defaultAddress,
      createdAt: shippingAddress.createdAt != null ? shippingAddress.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedAt: shippingAddress.lastUpdatedAt != null ? shippingAddress.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: shippingAddress.createdBy,
      lastUpdatedBy: shippingAddress.lastUpdatedBy,
      user: shippingAddress.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const shippingAddress = this.createFromForm();
    if (shippingAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.shippingAddressService.update(shippingAddress));
    } else {
      this.subscribeToSaveResponse(this.shippingAddressService.create(shippingAddress));
    }
  }

  private createFromForm(): IShippingAddress {
    const entity = {
      ...new ShippingAddress(),
      id: this.editForm.get(['id']).value,
      address1: this.editForm.get(['address1']).value,
      address2: this.editForm.get(['address2']).value,
      phone1: this.editForm.get(['phone1']).value,
      phone2: this.editForm.get(['phone2']).value,
      email1: this.editForm.get(['email1']).value,
      email2: this.editForm.get(['email2']).value,
      zipCode: this.editForm.get(['zipCode']).value,
      city: this.editForm.get(['city']).value,
      district: this.editForm.get(['district']).value,
      defaultAddress: this.editForm.get(['defaultAddress']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      lastUpdatedAt:
        this.editForm.get(['lastUpdatedAt']).value != null
          ? moment(this.editForm.get(['lastUpdatedAt']).value, DATE_TIME_FORMAT)
          : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      lastUpdatedBy: this.editForm.get(['lastUpdatedBy']).value,
      user: this.editForm.get(['user']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShippingAddress>>) {
    result.subscribe((res: HttpResponse<IShippingAddress>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
