import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderLineItem } from 'app/shared/model/order-line-item.model';
import { OrderLineItemService } from './order-line-item.service';

@Component({
  selector: 'jhi-order-line-item-delete-dialog',
  templateUrl: './order-line-item-delete-dialog.component.html'
})
export class OrderLineItemDeleteDialogComponent {
  orderLineItem: IOrderLineItem;

  constructor(
    protected orderLineItemService: OrderLineItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderLineItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderLineItemListModification',
        content: 'Deleted an orderLineItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-line-item-delete-popup',
  template: ''
})
export class OrderLineItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderLineItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderLineItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderLineItem = orderLineItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/order-line-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/order-line-item', { outlets: { popup: null } }]);
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
