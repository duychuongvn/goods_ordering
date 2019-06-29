import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExchangeRate } from 'app/shared/model/exchange-rate.model';
import { ExchangeRateService } from './exchange-rate.service';

@Component({
  selector: 'jhi-exchange-rate-delete-dialog',
  templateUrl: './exchange-rate-delete-dialog.component.html'
})
export class ExchangeRateDeleteDialogComponent {
  exchangeRate: IExchangeRate;

  constructor(
    protected exchangeRateService: ExchangeRateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.exchangeRateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'exchangeRateListModification',
        content: 'Deleted an exchangeRate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-exchange-rate-delete-popup',
  template: ''
})
export class ExchangeRateDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ exchangeRate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ExchangeRateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.exchangeRate = exchangeRate;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/exchange-rate', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/exchange-rate', { outlets: { popup: null } }]);
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
