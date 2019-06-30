package com.github.duychuongvn.ordering.webparser.service;

import com.github.duychuongvn.ordering.webparser.exception.ConnectAeoJPException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.math.BigDecimal;

public class DataExtractBase {

    protected Document read(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new ConnectAeoJPException(e);
        }
    }

    protected BigDecimal parsePrice(String price) {
        String finalPrice = price.replace(",", "");
        if (finalPrice.contains("円")) {
            finalPrice = finalPrice.substring(0, price.indexOf("円"));
        }
        return new BigDecimal(finalPrice);
    }
}
