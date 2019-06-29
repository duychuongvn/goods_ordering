/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderLineItemComponent } from 'app/entities/order-line-item/order-line-item.component';
import { OrderLineItemService } from 'app/entities/order-line-item/order-line-item.service';
import { OrderLineItem } from 'app/shared/model/order-line-item.model';

describe('Component Tests', () => {
  describe('OrderLineItem Management Component', () => {
    let comp: OrderLineItemComponent;
    let fixture: ComponentFixture<OrderLineItemComponent>;
    let service: OrderLineItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderLineItemComponent],
        providers: []
      })
        .overrideTemplate(OrderLineItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderLineItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderLineItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderLineItem(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderLineItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
