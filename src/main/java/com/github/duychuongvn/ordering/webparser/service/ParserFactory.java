package com.github.duychuongvn.ordering.webparser.service;

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderSource;
import com.github.duychuongvn.ordering.webparser.dto.OriginProduct;

import java.util.EnumMap;
import java.util.Map;

public class ParserFactory {

    private static final Map<OrderSource, DataExtractor> EXTRACTOR_MAP = new EnumMap<OrderSource, DataExtractor>(OrderSource.class);

    static {

        EXTRACTOR_MAP.put(OrderSource.AEO_JP, new AeoJPDataExtractImpl());
        EXTRACTOR_MAP.put(OrderSource.ABC_MART, new AbcMartDataExtractImpl());
        EXTRACTOR_MAP.put(OrderSource.RAKUTEN, new RakutenDataExtractImpl());
    }

    private ParserFactory() {
    }

    public static OriginProduct extractProduct(String url) {
        DataExtractor dataExtractor = EXTRACTOR_MAP.get(OrderSource.get(url));
        return dataExtractor.parse(url);
    }
}
