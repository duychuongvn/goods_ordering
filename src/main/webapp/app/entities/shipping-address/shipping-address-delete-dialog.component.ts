import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShippingAddress } from 'app/shared/model/shipping-address.model';
import { ShippingAddressService } from './shipping-address.service';

@Component({
  selector: 'jhi-shipping-address-delete-dialog',
  templateUrl: './shipping-address-delete-dialog.component.html'
})
export class ShippingAddressDeleteDialogComponent {
  shippingAddress: IShippingAddress;

  constructor(
    protected shippingAddressService: ShippingAddressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shippingAddressService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'shippingAddressListModification',
        content: 'Deleted an shippingAddress'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-shipping-address-delete-popup',
  template: ''
})
export class ShippingAddressDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shippingAddress }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ShippingAddressDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.shippingAddress = shippingAddress;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/shipping-address', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/shipping-address', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
