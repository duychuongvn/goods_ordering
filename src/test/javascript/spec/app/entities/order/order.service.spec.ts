/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrderService } from 'app/entities/order/order.service';
import { IOrder, Order, OrderStatus, DeliveryStatus } from 'app/shared/model/order.model';

describe('Service Tests', () => {
  describe('Order Service', () => {
    let injector: TestBed;
    let service: OrderService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrder;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(OrderService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Order(
        0,
        'AAAAAAA',
        currentDate,
        OrderStatus.PENDING,
        DeliveryStatus.INIT,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            packingDate: currentDate.format(DATE_FORMAT),
            estimatedDeliverDate: currentDate.format(DATE_FORMAT),
            deliveredDate: currentDate.format(DATE_FORMAT),
            finishPaymentTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Order', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            packingDate: currentDate.format(DATE_FORMAT),
            estimatedDeliverDate: currentDate.format(DATE_FORMAT),
            deliveredDate: currentDate.format(DATE_FORMAT),
            finishPaymentTime: currentDate.format(DATE_TIME_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            orderDate: currentDate,
            packingDate: currentDate,
            estimatedDeliverDate: currentDate,
            deliveredDate: currentDate,
            finishPaymentTime: currentDate,
            createdAt: currentDate,
            lastUpdatedAt: currentDate
          },
          returnedFromService
        );
        service
          .create(new Order(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Order', async () => {
        const returnedFromService = Object.assign(
          {
            paymentCode: 'BBBBBB',
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            deliveryStatus: 'BBBBBB',
            exchangeRateId: 'BBBBBB',
            exchangeRate: 1,
            totalJpyPrice: 1,
            deliveryFeeVnd: 1,
            totalPayVnd: 1,
            depositedVnd: 1,
            paidVnd: 1,
            packingDate: currentDate.format(DATE_FORMAT),
            estimatedDeliverDate: currentDate.format(DATE_FORMAT),
            deliveredDate: currentDate.format(DATE_FORMAT),
            finishPaymentTime: currentDate.format(DATE_TIME_FORMAT),
            remark: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            email: 'BBBBBB',
            zipCode: 'BBBBBB',
            city: 'BBBBBB',
            district: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastUpdatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate,
            packingDate: currentDate,
            estimatedDeliverDate: currentDate,
            deliveredDate: currentDate,
            finishPaymentTime: currentDate,
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

      it('should return a list of Order', async () => {
        const returnedFromService = Object.assign(
          {
            paymentCode: 'BBBBBB',
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            deliveryStatus: 'BBBBBB',
            exchangeRateId: 'BBBBBB',
            exchangeRate: 1,
            totalJpyPrice: 1,
            deliveryFeeVnd: 1,
            totalPayVnd: 1,
            depositedVnd: 1,
            paidVnd: 1,
            packingDate: currentDate.format(DATE_FORMAT),
            estimatedDeliverDate: currentDate.format(DATE_FORMAT),
            deliveredDate: currentDate.format(DATE_FORMAT),
            finishPaymentTime: currentDate.format(DATE_TIME_FORMAT),
            remark: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            email: 'BBBBBB',
            zipCode: 'BBBBBB',
            city: 'BBBBBB',
            district: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastUpdatedBy: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            orderDate: currentDate,
            packingDate: currentDate,
            estimatedDeliverDate: currentDate,
            deliveredDate: currentDate,
            finishPaymentTime: currentDate,
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

      it('should delete a Order', async () => {
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
