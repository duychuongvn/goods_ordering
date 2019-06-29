package com.github.duychuongvn.ordering.webparser.service;

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderSource;
import com.github.duychuongvn.ordering.webparser.dto.AbcMartProduct;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AbcMartDataExtractImpl extends DataExtractBase implements DataExtractor<AbcMartProduct> {
    @Override
    public AbcMartProduct parse(String url) {
        AbcMartProduct abcMartProduct = new AbcMartProduct();

        Document document = read(url);

        abcMartProduct.setName(document.getElementsByClass("goods_name_")
                .first()
                .getElementsByTag("h1")
                .first().text());
        abcMartProduct.setId(document.getElementById("hidden_goods").val());

        Elements images = document.getElementsByClass("etc_goodsimg_item_");
        images.forEach(div -> {
            abcMartProduct.getImages().add(div.getElementsByTag("a")
                    .attr("href"));
        });

        String price = document.getElementsByClass("goodsspec_")
                .first()
                .getElementById("sell_price")
                .text();
        abcMartProduct.setReferenceUrl(url);
        abcMartProduct.setOriginPrice(parsePrice(price));
        abcMartProduct.setSalePrice(abcMartProduct.getOriginPrice());
        abcMartProduct.setSource(OrderSource.ABC_MART);
        return abcMartProduct;

    }
}
