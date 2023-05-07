package org.lisang.flash_sale.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.lisang.flash_sale.domain.base.BaseModel;
import org.lisang.flash_sale.domain.vo.base.ResultVO;
import org.lisang.flash_sale.domain.vo.base.SmartPageVO;
import org.lisang.flash_sale.util.SnowFlakeIDTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
public abstract class BaseController<S extends IService<T>, T extends BaseModel<T>> {
//public abstract class BaseController {


    @Autowired
    protected S service;


    @ApiOperation("获取分页搜索信息")
    @PostMapping("list")
    public ResultVO getSmartData(@RequestBody SmartPageVO<T> spage) {
        Page<T> page = spage.initPage();
        QueryWrapper<T> wrapper = spage.initWrapper();
        return ResultVO.ok(service.getBaseMapper().selectPage(page, wrapper));
    }

    /**
     * 查询所有数据
     *
     * @param smartPageVO
     * @return
     */
    @ApiOperation("获取全部信息[分页不使用]")
    @PostMapping("listAll")
    public ResultVO listAll(@RequestBody SmartPageVO<T> smartPageVO) {
        QueryWrapper<T> wrapper = smartPageVO.initWrapper();
        if (smartPageVO.getSort() != null && smartPageVO.getSort().getField() != null)
            wrapper.orderBy(true,
                    !smartPageVO.getSort().isReverse(),
                    smartPageVO.getSort().getField());
        else
            wrapper.orderBy(true, false, "update_time");
        return ResultVO.ok(service.getBaseMapper().selectList(wrapper));
    }

    /**
     * 新增
     *
     * @param t
     * @return //
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST}, origins = "*", maxAge = 3600)
    @ApiOperation("新增")
    @PostMapping("save")
    public ResultVO create(@RequestBody T t) {
        if (service.save(t)) {
            return ResultVO.ok("SUCCESS", t);
        } else {
            return ResultVO.error();
        }
    }


    /**
     * 批量更新数据
     *
     * @param tList 更新队列
     * @return 结果
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST}, origins = "*", maxAge = 3600)
    @PostMapping("batchUpdate")
    @ApiOperation("批量更新")
    public ResultVO batchUpdate(@RequestBody List<T> tList) {
        service.updateBatchById(tList);
        return ResultVO.ok("SUCCESS");
    }


    /**
     * 根据id获取实体对象
     *
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
    @ApiOperation("删除")
    @DeleteMapping("delete")
    public ResultVO delete(@RequestBody List<String> list) {
        if (null == list || list.size() == 0)
            return ResultVO.error("缺少Id");
        for (String id : list) {
            if (!service.removeById(id))
                return ResultVO.error();
        }
        return ResultVO.ok();
    }






}
