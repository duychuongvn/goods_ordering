/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { OrderLineItemService } from 'app/entities/order-line-item/order-line-item.service';
import { IOrderLineItem, OrderLineItem, OrderSource } from 'app/shared/model/order-line-item.model';

describe('Service Tests', () => {
  describe('OrderLineItem Service', () => {
    let injector: TestBed;
    let service: OrderLineItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrderLineItem;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(OrderLineItemService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new OrderLineItem(
        0,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        OrderSource.AEO_JP
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a OrderLineItem', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new OrderLineItem(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a OrderLineItem', async () => {
        const returnedFromService = Object.assign(
          {
            referenceUrl: 'BBBBBB',
            originPrice: 1,
            salePrice: 1,
            tax: 1,
            totalPay: 1,
            goodsName: 'BBBBBB',
            goodsId: 'BBBBBB',
            goodsSKU: 'BBBBBB',
            size: 'BBBBBB',
            quantity: 1,
            remark: 'BBBBBB',
            images: 'BBBBBB',
            source: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of OrderLineItem', async () => {
        const returnedFromService = Object.assign(
          {
            referenceUrl: 'BBBBBB',
            originPrice: 1,
            salePrice: 1,
            tax: 1,
            totalPay: 1,
            goodsName: 'BBBBBB',
            goodsId: 'BBBBBB',
            goodsSKU: 'BBBBBB',
            size: 'BBBBBB',
            quantity: 1,
            remark: 'BBBBBB',
            images: 'BBBBBB',
            source: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a OrderLineItem', async () => {
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
