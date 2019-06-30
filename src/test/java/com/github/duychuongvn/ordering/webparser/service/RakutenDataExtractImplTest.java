package com.github.duychuongvn.ordering.webparser.service;

import com.github.duychuongvn.ordering.webparser.dto.OriginProduct;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RakutenDataExtractImplTest {

    RakutenDataExtractImpl rakutenDataExtract = new RakutenDataExtractImpl();
    @Test
    void parse() {

        OriginProduct product = rakutenDataExtract.parse("https://item.rakuten.co.jp/naturum/2840947/");

        System.out.println(product);
    }
}
