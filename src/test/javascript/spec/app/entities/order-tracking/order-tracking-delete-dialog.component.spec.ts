/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderTrackingDeleteDialogComponent } from 'app/entities/order-tracking/order-tracking-delete-dialog.component';
import { OrderTrackingService } from 'app/entities/order-tracking/order-tracking.service';

describe('Component Tests', () => {
  describe('OrderTracking Management Delete Component', () => {
    let comp: OrderTrackingDeleteDialogComponent;
    let fixture: ComponentFixture<OrderTrackingDeleteDialogComponent>;
    let service: OrderTrackingService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderTrackingDeleteDialogComponent]
      })
        .overrideTemplate(OrderTrackingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderTrackingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTrackingService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
