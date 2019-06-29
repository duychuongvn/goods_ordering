import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GoodsorderSharedModule } from 'app/shared';
import {
  ShippingAddressComponent,
  ShippingAddressDetailComponent,
  ShippingAddressUpdateComponent,
  ShippingAddressDeletePopupComponent,
  ShippingAddressDeleteDialogComponent,
  shippingAddressRoute,
  shippingAddressPopupRoute
} from './';

const ENTITY_STATES = [...shippingAddressRoute, ...shippingAddressPopupRoute];

@NgModule({
  imports: [GoodsorderSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ShippingAddressComponent,
    ShippingAddressDetailComponent,
    ShippingAddressUpdateComponent,
    ShippingAddressDeleteDialogComponent,
    ShippingAddressDeletePopupComponent
  ],
  entryComponents: [
    ShippingAddressComponent,
    ShippingAddressUpdateComponent,
    ShippingAddressDeleteDialogComponent,
    ShippingAddressDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GoodsorderShippingAddressModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
