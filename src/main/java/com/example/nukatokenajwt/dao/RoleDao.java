package com.example.nukatokenajwt.dao;

import com.example.nukatokenajwt.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
