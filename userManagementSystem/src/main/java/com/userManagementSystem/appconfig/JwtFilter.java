package com.userManagementSystem.appconfig;

import com.userManagementSystem.entity.Employee;
import com.userManagementSystem.repository.EmployeeRepository;
import com.userManagementSystem.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;

    public JwtFilter(JwtService jwtService, EmployeeRepository employeeRepository) {
        this.jwtService = jwtService;
        this.employeeRepository = employeeRepository;
    }

    //runtime polymorphism/dynamic polymorphism
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenWithBearer = request.getHeader("Authorization");

        if (tokenWithBearer!=null && tokenWithBearer.startsWith("Bearer "))
        {
            String token = tokenWithBearer.substring(7);

            String usernameFromToken = jwtService.getUsernameFromToken(token);


            Optional<Employee> byUsername = employeeRepository.findByUsername(usernameFromToken);

            if (byUsername.isPresent())
            {
                Employee employee = byUsername.get();

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(employee,null, Collections.singleton(new SimpleGrantedAuthority(employee.getRole().name())));

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);

            }


        }
        filterChain.doFilter(request,response);


    }
}
