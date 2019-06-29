import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { GoodsorderSharedLibsModule, GoodsorderSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [GoodsorderSharedLibsModule, GoodsorderSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [GoodsorderSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GoodsorderSharedModule {
  static forRoot() {
    return {
      ngModule: GoodsorderSharedModule
    };
  }
}
