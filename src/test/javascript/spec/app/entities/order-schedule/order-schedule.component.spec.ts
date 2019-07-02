/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderScheduleComponent } from 'app/entities/order-schedule/order-schedule.component';
import { OrderScheduleService } from 'app/entities/order-schedule/order-schedule.service';
import { OrderSchedule } from 'app/shared/model/order-schedule.model';

describe('Component Tests', () => {
  describe('OrderSchedule Management Component', () => {
    let comp: OrderScheduleComponent;
    let fixture: ComponentFixture<OrderScheduleComponent>;
    let service: OrderScheduleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderScheduleComponent],
        providers: []
      })
        .overrideTemplate(OrderScheduleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderScheduleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderScheduleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderSchedule(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderSchedules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
