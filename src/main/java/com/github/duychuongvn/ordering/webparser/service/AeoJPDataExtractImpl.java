package com.github.duychuongvn.ordering.webparser.service;

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderSource;
import com.github.duychuongvn.ordering.webparser.dto.AeoJPProduct;
import com.github.duychuongvn.util.TaxUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AeoJPDataExtractImpl extends DataExtractBase implements DataExtractor<AeoJPProduct> {

    @Override
    public AeoJPProduct parse(String url) {

        AeoJPProduct aeoJPProduct = new AeoJPProduct();
        Document doc = read(url);
        Element div = doc.getElementById("pdp-carousel2");
        Elements images = div.getElementsByTag("img");

        images.stream()
                .map(x -> x.attr("src").substring(0, x.attr("src").lastIndexOf("?")))
                .distinct()
                .forEach(img -> {
                    aeoJPProduct.getImages().add(img);
                });

        aeoJPProduct.setId(doc.getElementById("hidden_goods").val());
        aeoJPProduct.setName(doc.getElementById("hidden_goods_name").val());

        String originPrice = doc.getElementById("psp-regular-price")
                .getAllElements().first().text();

        aeoJPProduct.setOriginPrice(parsePrice(originPrice));

        Element saleDiv = doc.getElementById("psp-sale-price");
        if(saleDiv != null) {
            String salePrice = saleDiv
                .getAllElements().first().text();
            aeoJPProduct.setSalePrice(parsePrice(salePrice));

        } else {
            aeoJPProduct.setSalePrice(aeoJPProduct.getOriginPrice());
        }
        aeoJPProduct.setReferenceUrl(url);
        aeoJPProduct.setSku(doc.getElementById("hidden_goods_sku").val());
        aeoJPProduct.setTax(TaxUtil.getTax(OrderSource.AEO_JP, aeoJPProduct.getSalePrice()));
        aeoJPProduct.setSource(OrderSource.AEO_JP);
        return aeoJPProduct;
    }



}
