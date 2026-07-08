package ir.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //private final JwtService jwtService;
@Autowired private JwtService jwtService;
@Autowired private CustomUserDetailsService userDetailsService;
   public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {
              //  String token = header.substring(7);//String userId = jwtService.extractUserId(token);//UserContextHolder.setUserId(userId);
            String JWT = header.substring(7);
            if (jwtService.validateToken(JWT)) {
                String username=jwtService.extractUsername(JWT);
                UserDetails ud = userDetailsService.loadUserByUsername(username);

               var  u= new UsernamePasswordAuthenticationToken(ud,null,ud.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(u);
            }
            }

            filterChain.doFilter(request, response);

        } finally {

            UserContextHolder.clear();
        }
    }
}
