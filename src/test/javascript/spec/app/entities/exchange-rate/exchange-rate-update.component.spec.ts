/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GoodsorderTestModule } from '../../../test.module';
import { ExchangeRateUpdateComponent } from 'app/entities/exchange-rate/exchange-rate-update.component';
import { ExchangeRateService } from 'app/entities/exchange-rate/exchange-rate.service';
import { ExchangeRate } from 'app/shared/model/exchange-rate.model';

describe('Component Tests', () => {
  describe('ExchangeRate Management Update Component', () => {
    let comp: ExchangeRateUpdateComponent;
    let fixture: ComponentFixture<ExchangeRateUpdateComponent>;
    let service: ExchangeRateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [ExchangeRateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExchangeRateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExchangeRateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExchangeRateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExchangeRate(123);
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
        const entity = new ExchangeRate();
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
