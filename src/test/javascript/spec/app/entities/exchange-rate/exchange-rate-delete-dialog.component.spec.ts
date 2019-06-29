/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GoodsorderTestModule } from '../../../test.module';
import { ExchangeRateDeleteDialogComponent } from 'app/entities/exchange-rate/exchange-rate-delete-dialog.component';
import { ExchangeRateService } from 'app/entities/exchange-rate/exchange-rate.service';

describe('Component Tests', () => {
  describe('ExchangeRate Management Delete Component', () => {
    let comp: ExchangeRateDeleteDialogComponent;
    let fixture: ComponentFixture<ExchangeRateDeleteDialogComponent>;
    let service: ExchangeRateService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [ExchangeRateDeleteDialogComponent]
      })
        .overrideTemplate(ExchangeRateDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExchangeRateDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExchangeRateService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
