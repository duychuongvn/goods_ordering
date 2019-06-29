/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderTrackingDetailComponent } from 'app/entities/order-tracking/order-tracking-detail.component';
import { OrderTracking } from 'app/shared/model/order-tracking.model';

describe('Component Tests', () => {
  describe('OrderTracking Management Detail Component', () => {
    let comp: OrderTrackingDetailComponent;
    let fixture: ComponentFixture<OrderTrackingDetailComponent>;
    const route = ({ data: of({ orderTracking: new OrderTracking(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderTrackingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderTrackingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderTrackingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderTracking).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
