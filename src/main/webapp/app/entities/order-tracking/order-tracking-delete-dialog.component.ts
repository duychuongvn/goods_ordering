import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderTracking } from 'app/shared/model/order-tracking.model';
import { OrderTrackingService } from './order-tracking.service';

@Component({
  selector: 'jhi-order-tracking-delete-dialog',
  templateUrl: './order-tracking-delete-dialog.component.html'
})
export class OrderTrackingDeleteDialogComponent {
  orderTracking: IOrderTracking;

  constructor(
    protected orderTrackingService: OrderTrackingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderTrackingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderTrackingListModification',
        content: 'Deleted an orderTracking'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-tracking-delete-popup',
  template: ''
})
export class OrderTrackingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderTracking }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderTrackingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderTracking = orderTracking;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/order-tracking', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/order-tracking', { outlets: { popup: null } }]);
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
