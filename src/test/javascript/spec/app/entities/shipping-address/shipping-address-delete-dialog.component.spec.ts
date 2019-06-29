/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GoodsorderTestModule } from '../../../test.module';
import { ShippingAddressDeleteDialogComponent } from 'app/entities/shipping-address/shipping-address-delete-dialog.component';
import { ShippingAddressService } from 'app/entities/shipping-address/shipping-address.service';

describe('Component Tests', () => {
  describe('ShippingAddress Management Delete Component', () => {
    let comp: ShippingAddressDeleteDialogComponent;
    let fixture: ComponentFixture<ShippingAddressDeleteDialogComponent>;
    let service: ShippingAddressService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [ShippingAddressDeleteDialogComponent]
      })
        .overrideTemplate(ShippingAddressDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShippingAddressDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShippingAddressService);
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
