import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GoodsorderSharedModule } from 'app/shared';
import {
  OrderTrackingComponent,
  OrderTrackingDetailComponent,
  OrderTrackingUpdateComponent,
  OrderTrackingDeletePopupComponent,
  OrderTrackingDeleteDialogComponent,
  orderTrackingRoute,
  orderTrackingPopupRoute
} from './';

const ENTITY_STATES = [...orderTrackingRoute, ...orderTrackingPopupRoute];

@NgModule({
  imports: [GoodsorderSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderTrackingComponent,
    OrderTrackingDetailComponent,
    OrderTrackingUpdateComponent,
    OrderTrackingDeleteDialogComponent,
    OrderTrackingDeletePopupComponent
  ],
  entryComponents: [
    OrderTrackingComponent,
    OrderTrackingUpdateComponent,
    OrderTrackingDeleteDialogComponent,
    OrderTrackingDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GoodsorderOrderTrackingModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
