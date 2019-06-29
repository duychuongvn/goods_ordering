package com.github.duychuongvn.ordering.webparser.service;

import com.github.duychuongvn.ordering.webparser.dto.OriginProduct;

public interface DataExtractor<T extends OriginProduct> {

    T parse(String url);
}
