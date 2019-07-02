import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderSchedule } from 'app/shared/model/order-schedule.model';
import { OrderScheduleService } from './order-schedule.service';

@Component({
  selector: 'jhi-order-schedule-delete-dialog',
  templateUrl: './order-schedule-delete-dialog.component.html'
})
export class OrderScheduleDeleteDialogComponent {
  orderSchedule: IOrderSchedule;

  constructor(
    protected orderScheduleService: OrderScheduleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderScheduleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderScheduleListModification',
        content: 'Deleted an orderSchedule'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-schedule-delete-popup',
  template: ''
})
export class OrderScheduleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderSchedule }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderScheduleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderSchedule = orderSchedule;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/order-schedule', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/order-schedule', { outlets: { popup: null } }]);
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
