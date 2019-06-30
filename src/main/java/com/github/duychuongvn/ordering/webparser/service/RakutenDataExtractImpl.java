package com.github.duychuongvn.ordering.webparser.service;

import com.github.duychuongvn.exception.TechnicalException;
import com.github.duychuongvn.goodsorder.domain.enumeration.OrderSource;
import com.github.duychuongvn.ordering.webparser.dto.OriginProduct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class RakutenDataExtractImpl extends DataExtractBase implements DataExtractor<OriginProduct> {
    @Override
    public OriginProduct parse(String url) {

        OriginProduct product = new OriginProduct();
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url), 15000);
        } catch (IOException e) {
            throw new TechnicalException(e);
        }
        product.setName(doc.getElementsByClass("item_name")
            .first().text());

        doc.getElementsByClass("rakutenLimitedId_ImageMain1-3").forEach(image->{
            product.getImages().add(image.attr("href"));
        });
        String price = doc.getElementById("priceCalculationConfig").attr("data-price");
        product.setOriginPrice(parsePrice(price));
        product.setSalePrice(product.getOriginPrice());
        product.setSource(OrderSource.RAKUTEN);
        product.setReferenceUrl(url);
        product.setId(doc.getElementsByClass("item_number").first().text());
        return product;
    }
}
