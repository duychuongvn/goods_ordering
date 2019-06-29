/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ShippingAddressService } from 'app/entities/shipping-address/shipping-address.service';
import { IShippingAddress, ShippingAddress } from 'app/shared/model/shipping-address.model';

describe('Service Tests', () => {
  describe('ShippingAddress Service', () => {
    let injector: TestBed;
    let service: ShippingAddressService;
    let httpMock: HttpTestingController;
    let elemDefault: IShippingAddress;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ShippingAddressService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ShippingAddress(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ShippingAddress', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdAt: currentDate,
            lastUpdatedAt: currentDate
          },
          returnedFromService
        );
        service
          .create(new ShippingAddress(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ShippingAddress', async () => {
        const returnedFromService = Object.assign(
          {
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            email: 'BBBBBB',
            zipCode: 'BBBBBB',
            city: 'BBBBBB',
            district: 'BBBBBB',
            defaultAddress: true,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastUpdatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            lastUpdatedAt: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ShippingAddress', async () => {
        const returnedFromService = Object.assign(
          {
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            email: 'BBBBBB',
            zipCode: 'BBBBBB',
            city: 'BBBBBB',
            district: 'BBBBBB',
            defaultAddress: true,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastUpdatedBy: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdAt: currentDate,
            lastUpdatedAt: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ShippingAddress', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
