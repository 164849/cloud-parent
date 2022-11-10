package com.itck.goodsddd.infra.repo;

import com.itck.entity.ddd.GoodsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsItemDao extends JpaRepository<GoodsItem, Integer> {
}
