package org.lisang.flash_sale.annotation;


import org.lisang.flash_sale.domain.enums.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
public @interface Auth {

    UserRoleEnum role = null;

}
