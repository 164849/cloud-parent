package com.itck.skill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itck.skill.dto.SkillGoodsDetailDto;
import com.itck.skill.dto.SkillGoodsDto;
import com.itck.skill.entity.Skillgoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SkillGoodsDao extends BaseMapper<Skillgoods> {

    /**
     * 更新商品状态
     *
     * @param dto dto
     * @return int
     */
    @Update("UPDATE t_skillgoods SET flag=#{flag} WHERE id=#{id}")
    int updateFlag(SkillGoodsDto dto);

    /**
     * 更新商品状态和商品的静态页面地址
     * 商品上架后使用的：商品状态+静态页面地址
     *
     * @param flag    zhuangt
     * @param htmlUrl htmlurl
     * @param id      id
     * @return int
     */
    @Update("UPDATE t_skillgoods SET flag=#{flag},htmlurl=#{htmlUrl} WHERE id=#{id}")
    int updateFlagUrl(@Param("flag") Integer flag, @Param("htmlUrl") String htmlUrl, @Param("id") Integer id);

    // 这里需要联表查询
    @Select("select sa.id as said,sa.title,sa.stime,sa.etime,\n" +
            "       sg.id as sgid,sg.title as gtitle,\n" +
            "       sg.flag,sg.stock,sg.currprice,sg.price,sg.info,\n" +
            "       sg.picurl\n" +
            "from t_skillactivity sa\n" +
            "join t_skillgoods sg\n" +
            "on sa.id=sg.said\n" +
            "where sg.id=#{gid}")
    SkillGoodsDetailDto selectByGid(int gid);

    /**
     * 更新库存
     *
     * @param id  id
     * @param num 下单的数量
     * @return int
     */
    @Update("update t_skillgoods set stock=stock+#{num} where id=#{id}")
    int updateStock(@Param("id") int id, @Param("num") int num);


}
