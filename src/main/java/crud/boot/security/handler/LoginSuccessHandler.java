package crud.boot.security.handler;

import crud.boot.dto.RoleItem;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String context = httpServletRequest.getContextPath();
        if (roles.contains(RoleItem.ADMIN.getAuthority())) {
            httpServletResponse.sendRedirect(context + "/admin");
        } else if (roles.contains(RoleItem.USER.getAuthority())) {
            httpServletResponse.sendRedirect(context + "/user");
        } else {
            httpServletResponse.sendRedirect(context + "/");
        }
    }


}
