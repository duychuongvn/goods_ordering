/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoodsorderTestModule } from '../../../test.module';
import { ExchangeRateDetailComponent } from 'app/entities/exchange-rate/exchange-rate-detail.component';
import { ExchangeRate } from 'app/shared/model/exchange-rate.model';

describe('Component Tests', () => {
  describe('ExchangeRate Management Detail Component', () => {
    let comp: ExchangeRateDetailComponent;
    let fixture: ComponentFixture<ExchangeRateDetailComponent>;
    const route = ({ data: of({ exchangeRate: new ExchangeRate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [ExchangeRateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExchangeRateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExchangeRateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.exchangeRate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
