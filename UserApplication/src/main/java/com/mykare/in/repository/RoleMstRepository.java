package com.mykare.in.repository;

import com.mykare.in.entity.RoleMst;
import com.mykare.in.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMstRepository extends JpaRepository<RoleMst,Long> {

    RoleMst findByRoleName(RoleType roleName);
}
