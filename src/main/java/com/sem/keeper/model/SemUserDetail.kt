package com.sem.keeper.model

import com.sem.keeper.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.ArrayList



class SemUserDetail(user: UserEntity) :
    User(
        user.email, user.password,
        true, true, true, true,
        getAuthorities(user.roles!!)
    ) {
    companion object {
        private fun getAuthorities(roles: List<String>): List<GrantedAuthority> {
            val authorities: MutableList<GrantedAuthority> = ArrayList()
            for (role in roles) {
                authorities.add(SimpleGrantedAuthority(role))
            }
            return authorities
        }
    }
}
