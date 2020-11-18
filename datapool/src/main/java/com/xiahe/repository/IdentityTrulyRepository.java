package com.xiahe.repository;

import com.xiahe.entity.IdentityTruly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @description 仓库
 * @author: Yue
 * @create: 2019.12.31 00:06
 **/
@Repository
public interface IdentityTrulyRepository extends JpaRepository<IdentityTruly, Integer> {

    @Modifying
    @Query("update IdentityTruly set truly = ?2, message = ?3 where num = ?1")
    void updateByNum(String num, Boolean truly, String message);

}
