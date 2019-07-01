package com.github.duychuongvn.ordering.webparser.service;

import com.github.duychuongvn.ordering.webparser.dto.AeoJPProduct;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AeoJPDataExtractImplTest {
    @InjectMocks
    private AeoJPDataExtractImpl aeoJPDataExtract;
@org.junit.Test
   public void parse() {
        String url = "https://www.aeo.jp/shop/g/g11649214100";
        AeoJPProduct product = aeoJPDataExtract.parse(url);
        System.out.println(product.toString());
    }
}
