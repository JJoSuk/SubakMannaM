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
        User principal = (User) session.getAttribute("principal");

        // 로그인되지 않은 사용자에 대한 처리
        if (principal == null) {
            // /board 경로에서는 로그인이 필요하지 않은 경우
            if (request.getRequestURI().startsWith("/board")) {
                response.sendRedirect("/user/login");
                return false; // 요청 중단
            }
            // 로그인이 필요한 다른 경로로 리다이렉트
            response.sendRedirect("/user/login");
            return false; // 요청 중단
        }

        return true; // 인증된 사용자, 계속 진행
    }
}
