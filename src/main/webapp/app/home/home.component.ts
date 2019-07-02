import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IOrderLineItem, OrderLineItem } from 'app/shared/model/order-line-item.model';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order';
import { OrderLineItemService } from 'app/entities/order-line-item';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.scss']
})
export class HomeComponent implements OnInit {
  orderLineItem: IOrderLineItem;
  isSaving: boolean;

  orders: IOrder[];

  editForm = this.fb.group({
    referenceUrl: [undefined, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected orderLineItemService: OrderLineItemService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    // this.isSaving = false;
    // this.activatedRoute.data.subscribe(({ orderLineItem }) => {
    //   if (orderLineItem) {
    //     this.updateForm(orderLineItem);
    //     this.orderLineItem = orderLineItem;
    //   }
    // });
    // this.orderService
    //   .query()
    //   .pipe(
    //     filter((mayBeOk: HttpResponse<IOrder[]>) => mayBeOk.ok),
    //     map((response: HttpResponse<IOrder[]>) => response.body)
    //   )
    //   .subscribe((res: IOrder[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(orderLineItem: IOrderLineItem) {
    this.editForm.patchValue({
      id: orderLineItem.id,
      referenceUrl: orderLineItem.referenceUrl
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orderLineItem = this.createFromForm();
    if (orderLineItem.id !== undefined) {
      this.subscribeToSaveResponse(this.orderLineItemService.update(orderLineItem));
    } else {
      this.subscribeToSaveResponse(this.orderLineItemService.create(orderLineItem));
    }
  }

  private createFromForm(): IOrderLineItem {
    const entity = {
      ...new OrderLineItem(),
      referenceUrl: this.editForm.get(['referenceUrl']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderLineItem>>) {
    result.subscribe(
      (res: HttpResponse<IOrderLineItem>) => this.onSaveSuccess(res.body.id),
      (res: HttpErrorResponse) => this.onSaveError()
    );
  }

  protected onSaveSuccess(orderId: number) {
    this.isSaving = false;
    this.router.navigate(['order', orderId, 'view']);
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOrderById(index: number, item: IOrder) {
    return item.id;
  }
}
