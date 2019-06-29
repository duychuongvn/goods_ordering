/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GoodsorderTestModule } from '../../../test.module';
import { ShippingAddressUpdateComponent } from 'app/entities/shipping-address/shipping-address-update.component';
import { ShippingAddressService } from 'app/entities/shipping-address/shipping-address.service';
import { ShippingAddress } from 'app/shared/model/shipping-address.model';

describe('Component Tests', () => {
  describe('ShippingAddress Management Update Component', () => {
    let comp: ShippingAddressUpdateComponent;
    let fixture: ComponentFixture<ShippingAddressUpdateComponent>;
    let service: ShippingAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [ShippingAddressUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ShippingAddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShippingAddressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShippingAddressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShippingAddress(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShippingAddress();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
