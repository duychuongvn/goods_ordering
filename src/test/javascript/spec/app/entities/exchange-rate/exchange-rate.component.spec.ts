/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GoodsorderTestModule } from '../../../test.module';
import { ExchangeRateComponent } from 'app/entities/exchange-rate/exchange-rate.component';
import { ExchangeRateService } from 'app/entities/exchange-rate/exchange-rate.service';
import { ExchangeRate } from 'app/shared/model/exchange-rate.model';

describe('Component Tests', () => {
  describe('ExchangeRate Management Component', () => {
    let comp: ExchangeRateComponent;
    let fixture: ComponentFixture<ExchangeRateComponent>;
    let service: ExchangeRateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [ExchangeRateComponent],
        providers: []
      })
        .overrideTemplate(ExchangeRateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExchangeRateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExchangeRateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ExchangeRate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.exchangeRates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
