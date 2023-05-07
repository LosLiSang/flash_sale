package org.lisang.flash_sale.domain.param;

import lombok.Data;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;

import java.util.List;

@Data
public class PageOrderParam {

    private List<OrderStatusEnum> payStateList;

    private String activityName;

    private String username;

}
