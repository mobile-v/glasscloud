package ru.vmsystems.glasscloud.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.user.UserRepository
import java.util.*

@Service
class UserDetailsServiceImpl(
        val userRepository: UserRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(login: String): UserDetails {

        val user = userRepository.getByLogin(login) ?: throw UsernameNotFoundException("Пользователь не найден")

        val roles = HashSet<GrantedAuthority>()
        roles.add(SimpleGrantedAuthority(user.role))

        return User(
                user.login,
                user.password,
                user.enabled,
                true, true, true,
                roles)
    }
}
