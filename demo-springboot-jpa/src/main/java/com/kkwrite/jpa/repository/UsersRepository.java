package com.kkwrite.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkwrite.jpa.pojo.UsersDO;

/** 
 * UsersRepository
 *
 * @author Soosky Wang
 * @date 2018年7月24日 下午5:40:42 
 * @version 1.0.0
 */
public interface UsersRepository extends JpaRepository<UsersDO, String> {

	UsersDO findByUsername(String username);
}
