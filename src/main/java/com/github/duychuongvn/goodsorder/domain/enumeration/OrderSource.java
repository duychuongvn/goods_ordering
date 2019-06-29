package com.github.duychuongvn.goodsorder.domain.enumeration;

/**
 * The OrderSource enumeration.
 */
public enum OrderSource {

    AEO_JP("AEOJP", "aeo.jp"),
    ABC_MART("ABC_MART", "abc-mart.net");

    private String domain;
    private String code;


    OrderSource(String code, String domain) {
        this.code = code;
        this.domain = domain;
    }

    public static OrderSource get(String url) {
        for (OrderSource source : OrderSource.values()) {
            if (url.contains(source.domain)) {
                return source;
            }
        }
        throw new IllegalArgumentException("Do not support: " + url);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
