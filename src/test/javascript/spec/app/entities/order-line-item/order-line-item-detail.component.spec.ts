/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderLineItemDetailComponent } from 'app/entities/order-line-item/order-line-item-detail.component';
import { OrderLineItem } from 'app/shared/model/order-line-item.model';

describe('Component Tests', () => {
  describe('OrderLineItem Management Detail Component', () => {
    let comp: OrderLineItemDetailComponent;
    let fixture: ComponentFixture<OrderLineItemDetailComponent>;
    const route = ({ data: of({ orderLineItem: new OrderLineItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderLineItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderLineItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderLineItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderLineItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
