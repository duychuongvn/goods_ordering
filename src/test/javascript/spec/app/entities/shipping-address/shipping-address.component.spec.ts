/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GoodsorderTestModule } from '../../../test.module';
import { ShippingAddressComponent } from 'app/entities/shipping-address/shipping-address.component';
import { ShippingAddressService } from 'app/entities/shipping-address/shipping-address.service';
import { ShippingAddress } from 'app/shared/model/shipping-address.model';

describe('Component Tests', () => {
  describe('ShippingAddress Management Component', () => {
    let comp: ShippingAddressComponent;
    let fixture: ComponentFixture<ShippingAddressComponent>;
    let service: ShippingAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodsorderTestModule],
        declarations: [ShippingAddressComponent],
        providers: []
      })
        .overrideTemplate(ShippingAddressComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShippingAddressComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShippingAddressService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ShippingAddress(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.shippingAddresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
