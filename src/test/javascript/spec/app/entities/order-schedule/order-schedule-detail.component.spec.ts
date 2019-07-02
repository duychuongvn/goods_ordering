/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderScheduleDetailComponent } from 'app/entities/order-schedule/order-schedule-detail.component';
import { OrderSchedule } from 'app/shared/model/order-schedule.model';

describe('Component Tests', () => {
  describe('OrderSchedule Management Detail Component', () => {
    let comp: OrderScheduleDetailComponent;
    let fixture: ComponentFixture<OrderScheduleDetailComponent>;
    const route = ({ data: of({ orderSchedule: new OrderSchedule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderScheduleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderScheduleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderScheduleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderSchedule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
