import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GoodsorderSharedModule } from 'app/shared';
import {
  OrderLineItemComponent,
  OrderLineItemDetailComponent,
  OrderLineItemUpdateComponent,
  OrderLineItemDeletePopupComponent,
  OrderLineItemDeleteDialogComponent,
  orderLineItemRoute,
  orderLineItemPopupRoute
} from './';

const ENTITY_STATES = [...orderLineItemRoute, ...orderLineItemPopupRoute];

@NgModule({
  imports: [GoodsorderSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderLineItemComponent,
    OrderLineItemDetailComponent,
    OrderLineItemUpdateComponent,
    OrderLineItemDeleteDialogComponent,
    OrderLineItemDeletePopupComponent
  ],
  entryComponents: [
    OrderLineItemComponent,
    OrderLineItemUpdateComponent,
    OrderLineItemDeleteDialogComponent,
    OrderLineItemDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GoodsorderOrderLineItemModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
