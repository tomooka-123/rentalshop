package jp.sun.rental.common.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage;

        if (exception instanceof DisabledException) {
            errorMessage = "このアカウントは退会済みです";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "ユーザー名またはパスワードが違います";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "ユーザーが存在しません";
        } else {
            errorMessage = "ログインに失敗しました";
        }

        request.getSession().setAttribute("LOGIN_ERROR", errorMessage);
        response.sendRedirect("/login?error");
    }
}
