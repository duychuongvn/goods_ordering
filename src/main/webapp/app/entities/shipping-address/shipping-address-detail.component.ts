import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShippingAddress } from 'app/shared/model/shipping-address.model';

@Component({
  selector: 'jhi-shipping-address-detail',
  templateUrl: './shipping-address-detail.component.html'
})
export class ShippingAddressDetailComponent implements OnInit {
  shippingAddress: IShippingAddress;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shippingAddress }) => {
      this.shippingAddress = shippingAddress;
    });
  }

  previousState() {
    window.history.back();
  }
}
