package kr.co.mannam.domain.config.common;


import kr.co.mannam.domain.entity.member.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션에 회원정보가 존재하는지 확인
        HttpSession session = request.getSession();

        User principal = (User)session.getAttribute("principal");

        if (principal == null){
            response.sendRedirect("/user/login");
        }

        return true;
    }
}
