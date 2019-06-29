/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderLineItemDeleteDialogComponent } from 'app/entities/order-line-item/order-line-item-delete-dialog.component';
import { OrderLineItemService } from 'app/entities/order-line-item/order-line-item.service';

describe('Component Tests', () => {
  describe('OrderLineItem Management Delete Component', () => {
    let comp: OrderLineItemDeleteDialogComponent;
    let fixture: ComponentFixture<OrderLineItemDeleteDialogComponent>;
    let service: OrderLineItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderLineItemDeleteDialogComponent]
      })
        .overrideTemplate(OrderLineItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderLineItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderLineItemService);
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
