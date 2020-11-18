package com.xiahe.repository;

import com.xiahe.entity.IdentityGov;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description 仓库
 * @author: Yue
 * @create: 2019.12.31 00:06
 **/
@Repository
public interface IdentityGovRepository extends JpaRepository<IdentityGov, Integer> {

}
