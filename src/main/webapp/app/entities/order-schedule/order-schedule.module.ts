import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GoodsorderSharedModule } from 'app/shared';
import {
  OrderScheduleComponent,
  OrderScheduleDetailComponent,
  OrderScheduleUpdateComponent,
  OrderScheduleDeletePopupComponent,
  OrderScheduleDeleteDialogComponent,
  orderScheduleRoute,
  orderSchedulePopupRoute
} from './';

const ENTITY_STATES = [...orderScheduleRoute, ...orderSchedulePopupRoute];

@NgModule({
  imports: [GoodsorderSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderScheduleComponent,
    OrderScheduleDetailComponent,
    OrderScheduleUpdateComponent,
    OrderScheduleDeleteDialogComponent,
    OrderScheduleDeletePopupComponent
  ],
  entryComponents: [
    OrderScheduleComponent,
    OrderScheduleUpdateComponent,
    OrderScheduleDeleteDialogComponent,
    OrderScheduleDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GoodsorderOrderScheduleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
