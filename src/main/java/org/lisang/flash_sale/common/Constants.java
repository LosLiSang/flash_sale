package org.lisang.flash_sale.common;

public class Constants {

    public final static Long EXPIRE_SECOND = (long) (60 * 60 * 24 * 7);
    public final static String REDIS_USER_PREFIX = "user_";
    public final static String REDIS_TOKEN_PREFIX = "token_";
    public final static String REDIS_ACTIVITY_PREFIX = "activity_";
    public final static String REDIS_STOCK_PREFIX = "stock_";
    public final static String REDIS_STOCK_ALL_COUNT = "stock";
    public final static String REDIS_STOCK_SALE_COUNT = "sale";
    public final static String REDIS_ORDER_PREFIX = "order_";


    public final static String MQ_TOPIC = "FLASH_SALE_TOPIC";
    public final static String MQ_ORDER_TAG = "ORDER";
    public final static String MQ_ORDER_STATUS_CHECK_TAG = "ORDER_STATUS";
}
