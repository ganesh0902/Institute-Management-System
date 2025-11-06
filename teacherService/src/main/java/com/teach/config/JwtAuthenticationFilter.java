package com.teach.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.net.HttpHeaders;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    //  Define all public endpoints here that don’t require authentication
    private static final List<String> PUBLIC_URLS = List.of(
        "/teacher/teacherCount/**"
    );

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        System.out.println("🔹 [Teacher Filter] Request Path: " + path);

        // Skip authentication for public endpoints
        if (isPublicEndpoint(path)) {
            System.out.println("🟢 Public endpoint, skipping authentication.");
            filterChain.doFilter(request, response);
            return;
        }

        // Validate Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("⚠️ Missing or invalid Authorization header.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // Extract username & roles from JWT
            String username = jwtService.extractUsername(token);
            List<String> roles = jwtService.extractRoles(token);

            System.out.println("👤 Authenticated User: " + username);
            System.out.println("🔸 Roles: " + roles);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // ✅ Create authentication object & set it in context
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            // ❌ DO NOT THROW EXCEPTION - Handle gracefully
            System.out.println("❌ JWT Validation failed: " + e.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid or expired token.\"}");
            return; // ⛔ stop further processing
        }

        // ✅ Continue filter chain
        filterChain.doFilter(request, response);
    }

    // ✅ Utility: check if the current path matches any public endpoint pattern
    private boolean isPublicEndpoint(String path) {
        return PUBLIC_URLS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}



//private final AntPathMatcher pathMatcher = new AntPathMatcher();