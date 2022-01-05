package com.boticario.cashback.security

import com.boticario.cashback.repository.RevendedorRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(
    val autenticacaoService : AutenticacaoService,
    val tokenService : TokenService,
    val revendedorRepository: RevendedorRepository
    ) : WebSecurityConfigurerAdapter()
{

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }


    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(autenticacaoService)?.passwordEncoder(BCryptPasswordEncoder());
    }

    override fun configure(http: HttpSecurity) {

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/revendedores").permitAll()
            .antMatchers(HttpMethod.POST, "/revendedores/auth").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore( AutenticacaoViaTokenFilter(tokenService, revendedorRepository), UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(webSecurity: WebSecurity) {
        webSecurity.ignoring().
                antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**")
    }
}

