package org.itck.orders.controller;

import org.itck.orders.entity.TOrderlog;
import org.itck.orders.service.TOrderlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 15.订单状态变化表(TOrderlog)表控制层
 *
 * @author makejava
 * @since 2022-11-10 15:03:18
 */
@RestController
@RequestMapping("tOrderlog")
public class TOrderlogController {
    /**
     * 服务对象
     */
    @Resource
    private TOrderlogService tOrderlogService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<TOrderlog> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.tOrderlogService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param tOrderlog 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<TOrderlog> add(TOrderlog tOrderlog) {
        return ResponseEntity.ok(this.tOrderlogService.insert(tOrderlog));
    }

    /**
     * 编辑数据
     *
     * @param tOrderlog 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<TOrderlog> edit(TOrderlog tOrderlog) {
        return ResponseEntity.ok(this.tOrderlogService.update(tOrderlog));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.tOrderlogService.deleteById(id));
    }

}

