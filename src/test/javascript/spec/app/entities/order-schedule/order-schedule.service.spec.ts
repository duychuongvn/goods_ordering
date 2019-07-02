/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrderScheduleService } from 'app/entities/order-schedule/order-schedule.service';
import { IOrderSchedule, OrderSchedule, OrderScheduleStatus } from 'app/shared/model/order-schedule.model';

describe('Service Tests', () => {
  describe('OrderSchedule Service', () => {
    let injector: TestBed;
    let service: OrderScheduleService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrderSchedule;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(OrderScheduleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OrderSchedule(
        0,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        0,
        0,
        OrderScheduleStatus.OPEN,
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
            openDate: currentDate.format(DATE_FORMAT),
            closeDate: currentDate.format(DATE_FORMAT),
            expectedPackingDate: currentDate.format(DATE_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_FORMAT),
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

      it('should create a OrderSchedule', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            openDate: currentDate.format(DATE_FORMAT),
            closeDate: currentDate.format(DATE_FORMAT),
            expectedPackingDate: currentDate.format(DATE_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            openDate: currentDate,
            closeDate: currentDate,
            expectedPackingDate: currentDate,
            expectedDeliveryDate: currentDate,
            createdAt: currentDate,
            lastUpdatedAt: currentDate
          },
          returnedFromService
        );
        service
          .create(new OrderSchedule(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a OrderSchedule', async () => {
        const returnedFromService = Object.assign(
          {
            openDate: currentDate.format(DATE_FORMAT),
            closeDate: currentDate.format(DATE_FORMAT),
            expectedPackingDate: currentDate.format(DATE_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_FORMAT),
            maxOrderNumber: 1,
            currentOrderNumber: 1,
            status: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastUpdatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            openDate: currentDate,
            closeDate: currentDate,
            expectedPackingDate: currentDate,
            expectedDeliveryDate: currentDate,
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

      it('should return a list of OrderSchedule', async () => {
        const returnedFromService = Object.assign(
          {
            openDate: currentDate.format(DATE_FORMAT),
            closeDate: currentDate.format(DATE_FORMAT),
            expectedPackingDate: currentDate.format(DATE_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_FORMAT),
            maxOrderNumber: 1,
            currentOrderNumber: 1,
            status: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastUpdatedBy: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            openDate: currentDate,
            closeDate: currentDate,
            expectedPackingDate: currentDate,
            expectedDeliveryDate: currentDate,
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

      it('should delete a OrderSchedule', async () => {
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
