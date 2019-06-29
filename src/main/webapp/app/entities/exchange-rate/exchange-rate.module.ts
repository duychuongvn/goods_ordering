import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GoodsorderSharedModule } from 'app/shared';
import {
  ExchangeRateComponent,
  ExchangeRateDetailComponent,
  ExchangeRateUpdateComponent,
  ExchangeRateDeletePopupComponent,
  ExchangeRateDeleteDialogComponent,
  exchangeRateRoute,
  exchangeRatePopupRoute
} from './';

const ENTITY_STATES = [...exchangeRateRoute, ...exchangeRatePopupRoute];

@NgModule({
  imports: [GoodsorderSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ExchangeRateComponent,
    ExchangeRateDetailComponent,
    ExchangeRateUpdateComponent,
    ExchangeRateDeleteDialogComponent,
    ExchangeRateDeletePopupComponent
  ],
  entryComponents: [
    ExchangeRateComponent,
    ExchangeRateUpdateComponent,
    ExchangeRateDeleteDialogComponent,
    ExchangeRateDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GoodsorderExchangeRateModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
