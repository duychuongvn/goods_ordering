/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderLineItemUpdateComponent } from 'app/entities/order-line-item/order-line-item-update.component';
import { OrderLineItemService } from 'app/entities/order-line-item/order-line-item.service';
import { OrderLineItem } from 'app/shared/model/order-line-item.model';

describe('Component Tests', () => {
  describe('OrderLineItem Management Update Component', () => {
    let comp: OrderLineItemUpdateComponent;
    let fixture: ComponentFixture<OrderLineItemUpdateComponent>;
    let service: OrderLineItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderLineItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrderLineItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderLineItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderLineItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderLineItem(123);
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
        const entity = new OrderLineItem();
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
