package org.lisang.flash_sale.controller.base;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.lisang.flash_sale.domain.base.BaseModel;
import org.lisang.flash_sale.domain.vo.ResultVO;
import org.lisang.flash_sale.domain.vo.SmartPageVO;
import org.lisang.flash_sale.util.SnowFlakeIDTools;
import org.lisang.flash_sale.util.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public abstract class BaseController<S extends IService<T>, T extends BaseModel<T>> {

    @Autowired
    protected S service;


    @ApiOperation("获取分页搜索信息")
    @PostMapping("/list")
    public ResultVO getSmartData(@RequestBody SmartPageVO<T> spage) {
        Page<T> page = initPage(spage);
        QueryWrapper<T> wrapper = initWrapper(spage);
        return ResultVO.ok(service.getBaseMapper().selectPage(page, wrapper));
    }

    /**
     * 查询所有数据
     * @param smartPageVO
     * @return
     */
    @ApiOperation("获取全部信息[分页不使用]")
    @PostMapping("/listAll")
    public ResultVO listAll(@RequestBody SmartPageVO<T> smartPageVO) {
        QueryWrapper<T> wrapper = initWrapper(smartPageVO);
        if(smartPageVO.getSort() != null && smartPageVO.getSort().getField() != null)
            wrapper.orderBy(true,
                    !smartPageVO.getSort().isReverse(),
                    smartPageVO.getSort().getField());
        else
            wrapper.orderBy(true, false, "update_time");
        return ResultVO.ok(service.getBaseMapper().selectList(wrapper));
    }

    /**
     * 新增
     * @param t
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST}, origins = "*", maxAge = 3600)
    @ApiOperation("新增")
    @PostMapping("/save")
    public ResultVO create(@RequestBody T t) {
        t.setId(SnowFlakeIDTools.generateId());
        if (service.save(t)) {
            return ResultVO.ok("SUCCESS",t);
        } else {
            return ResultVO.error();
        }
    }


    /**
     * 批量更新数据
     * @param tList 更新队列
     * @return 结果
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST}, origins = "*", maxAge = 3600)
    @PostMapping("/batchUpdate")
    @ApiOperation("批量更新")
    public ResultVO batchUpdate(@RequestBody List<T> tList){
        service.updateBatchById(tList);
        return ResultVO.ok("SUCCESS");
    }


    /**
     * 更新
     * @param t
     * @return
     */
    @ApiOperation("更新")
    @PutMapping("/update")
    public ResultVO update(@RequestBody T t) {
        if (service.updateById(t)) {
            return ResultVO.ok("SUCCESS",t);
        } else {
            return ResultVO.error();
        }
    }


    /**
     * 根据id获取实体对象

     * @param id
     * @return
     */
    @ApiOperation("根据id获取实体对象")
    @GetMapping("get/{id}")
    public T getInfo(@PathVariable String id) {
        return service.getById(id);
    }

    /**
     * 删除
     *
     * @param
     * @return
     */
    @Transactional
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestBody List<String> list) {
        if(null == list || list.size() == 0)
            return ResultVO.error("缺少Id");
        for(String id : list){
            if (!service.removeById(id))
                return ResultVO.error();
        }
        return ResultVO.ok();
    }


//    @getUserDict(type = 1,fieldName = {"createUserId","updateUserId","teacherId","userId","adminId","classTeacher","assistant"} )
//    @ApiOperation(value = "将各类id转成名字的筛选列表", notes = "将各类id转成名字的筛选列表")
//    @PostMapping("/listWithUserDict")
//    public ResultVO listWithUserDict(String userId,@RequestBody SmartPageVO<T> spage){
//        Page<T> page = initPage(spage);
//        if (StringUtils.isBlank(spage.getSort().getPredicate())) {
//            spage.getSort().setPredicate("update_time");
//        }
//        page.setOrderByField(spage.getSort().getPredicate());
//        page.setAsc(!spage.getSort().getReverse());
//        EntityWrapper<T> wrapper = initWrapper(spage);
//        Page<T> Page =  service.selectPage(page, wrapper);
//        return ResultVO.ok(Page);
//    }

    /**
     * 构造初始化wrapper，spage为通用查询格式
     * @param spage
     * @return 返回查询结果集的封装
     */
    public QueryWrapper<T> initWrapper(SmartPageVO<T> spage) {
        QueryWrapper<T> wrapper = new QueryWrapper<T>();
        if (spage.getSearchPO() != null) {
            Class EntityClazz = spage.getSearchPO().getClass();
            Field[] fields;
            Field[] classFields = EntityClazz.getDeclaredFields();
            // getDeclaredFields只能获取到本类的属性，不能获取父类属性，专门对父类进行处理
            if(EntityClazz.getSuperclass() != null){
                Field[] superFields = EntityClazz.getSuperclass().getDeclaredFields();
                fields = new Field[classFields.length + superFields.length];
                System.arraycopy(classFields,0,fields,0,classFields.length);
                System.arraycopy(superFields,0,fields,classFields.length,superFields.length);
            } else {
                fields = classFields;
            }
            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    // 如果是临时字段则不进行查询
                     if (fields[i].getAnnotation(TableField.class) == null
                             && fields[i].getAnnotation(TableId.class) == null){
                         continue;
                     }
                    Object value = fields[i].get(spage.getSearchPO());
                    if (null != value && !"".equals(value)) {
                        String fieldName = StringTools.underscoreName(fields[i].getName());
                        // 如果是枚举
                        if (fields[i].getType().isEnum())
                            wrapper.eq(fieldName, ((IEnum) value).getValue());
                        else
                            wrapper.like(fieldName, value);
                    }
                    fields[i].setAccessible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return wrapper;
    }


    /**
     * 构造初始化page，spage为通用查询格式
     * 注意：page的order by 与 wrapper的order by的冲突
     * @param spage
     * @return
     */
    public Page<T> initPage(SmartPageVO<T> spage){
        // 初始化Page
        Page<T> page = new Page<>(spage.getPagination().getStart()
                , spage.getPagination().getNumber());
        // 设置默认排序
        if(spage.getSort() == null || StringUtils.isNoneEmpty(spage.getSort().getField())) {
            spage.getSort().setField("update_time");
        }
        // 设置排序
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn(spage.getSort().getField());
        orderItem.setAsc(spage.getSort().isReverse());
        page.addOrder(orderItem);
        return page;
    }

}
