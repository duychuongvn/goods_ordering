/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderTrackingComponent } from 'app/entities/order-tracking/order-tracking.component';
import { OrderTrackingService } from 'app/entities/order-tracking/order-tracking.service';
import { OrderTracking } from 'app/shared/model/order-tracking.model';

describe('Component Tests', () => {
  describe('OrderTracking Management Component', () => {
    let comp: OrderTrackingComponent;
    let fixture: ComponentFixture<OrderTrackingComponent>;
    let service: OrderTrackingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderTrackingComponent],
        providers: []
      })
        .overrideTemplate(OrderTrackingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderTrackingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTrackingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderTracking(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderTrackings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
