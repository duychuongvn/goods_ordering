/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrderTrackingService } from 'app/entities/order-tracking/order-tracking.service';
import { IOrderTracking, OrderTracking, DeliveryStatus } from 'app/shared/model/order-tracking.model';

describe('Service Tests', () => {
  describe('OrderTracking Service', () => {
    let injector: TestBed;
    let service: OrderTrackingService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrderTracking;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(OrderTrackingService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OrderTracking(0, DeliveryStatus.INIT, currentDate, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a OrderTracking', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateTime: currentDate.format(DATE_TIME_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateTime: currentDate,
            createdAt: currentDate,
            lastUpdatedAt: currentDate
          },
          returnedFromService
        );
        service
          .create(new OrderTracking(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a OrderTracking', async () => {
        const returnedFromService = Object.assign(
          {
            deliveryStatus: 'BBBBBB',
            dateTime: currentDate.format(DATE_TIME_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastUpdatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateTime: currentDate,
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

      it('should return a list of OrderTracking', async () => {
        const returnedFromService = Object.assign(
          {
            deliveryStatus: 'BBBBBB',
            dateTime: currentDate.format(DATE_TIME_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastUpdatedBy: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateTime: currentDate,
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

      it('should delete a OrderTracking', async () => {
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
