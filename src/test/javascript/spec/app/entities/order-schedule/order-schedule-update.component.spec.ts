/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GoodsorderTestModule } from '../../../test.module';
import { OrderScheduleUpdateComponent } from 'app/entities/order-schedule/order-schedule-update.component';
import { OrderScheduleService } from 'app/entities/order-schedule/order-schedule.service';
import { OrderSchedule } from 'app/shared/model/order-schedule.model';

describe('Component Tests', () => {
  describe('OrderSchedule Management Update Component', () => {
    let comp: OrderScheduleUpdateComponent;
    let fixture: ComponentFixture<OrderScheduleUpdateComponent>;
    let service: OrderScheduleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [OrderScheduleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrderScheduleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderScheduleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderScheduleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderSchedule(123);
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
        const entity = new OrderSchedule();
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
