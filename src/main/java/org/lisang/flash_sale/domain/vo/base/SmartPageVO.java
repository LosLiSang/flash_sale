package org.lisang.flash_sale.domain.vo.base;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lisang.flash_sale.util.StringTools;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Optional;


@Slf4j
@Data
public class SmartPageVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private SmartPagination pagination;

    private T entity;

    private SmartSort sort;



    @Override
    public String toString() {
        // Create a copy, don't share the array
        return this.getPagination().toString();
    }

    public QueryWrapper<T> initWrapper() {
        QueryWrapper<T> wrapper = new QueryWrapper<T>();
        if (this.getEntity() != null) {
            Class EntityClazz = this.getEntity().getClass();
            Field[] fields;
            Field[] classFields = EntityClazz.getDeclaredFields();
            // getDeclaredFields只能获取到本类的属性，不能获取父类属性，专门对父类进行处理
            if (EntityClazz.getSuperclass() != null) {
                Field[] superFields = EntityClazz.getSuperclass().getDeclaredFields();
                fields = new Field[classFields.length + superFields.length];
                System.arraycopy(classFields, 0, fields, 0, classFields.length);
                System.arraycopy(superFields, 0, fields, classFields.length, superFields.length);
            } else {
                fields = classFields;
            }
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                // 如果是临时字段则不进行查询
                if (fields[i].getAnnotation(TableField.class) == null
                        && fields[i].getAnnotation(TableId.class) == null) {
                    continue;
                }
                Object value = null;
                try {
                    value = fields[i].get(this.getEntity());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                log.info("fieldName: {} fieldValue: {}", fields[i].getName(), value);
                if (null != value && !"".equals(value)) {
                    String fieldName = StringTools.underscoreName(fields[i].getName());
                    // 如果是枚举
                    if (fields[i].getType().isEnum())
                        wrapper.eq(fieldName, ((IEnum) value).getValue());
                    else
                        wrapper.like(fieldName, value);
                }
                fields[i].setAccessible(false);
            }
        }
        return wrapper;
    }

    public Page<T> initPage() {
        // 初始化Page
        Integer current = Optional.ofNullable(pagination)
                .map(SmartPagination::getCurrent)
                .orElse(1);
        Integer pageSize = Optional.ofNullable(pagination)
                .map(SmartPagination::getPageSize)
                .orElse(8);
        Page<T> page = new Page<>(current, pageSize);
        // TODO 设置默认排序
        return page;
    }

}
