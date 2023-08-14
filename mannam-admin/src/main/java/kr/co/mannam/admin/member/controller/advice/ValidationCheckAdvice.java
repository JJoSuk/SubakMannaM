package kr.co.mannam.admin.member.controller.advice;

import kr.co.mannam.admin.member.dto.ResponseDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect // Aspect = Pointcut + Advice
public class ValidationCheckAdvice {

    // ProceedingJoinPoint 인터페이스 : 실행중인 메서드에 대한 정보를 제공하며,
    // 메서드 실행전과 실행 후 시점에 호출되는 proceed() 추상메서드를 갖고 있음.

    // proceed() : AOP가 적용된 메서드의 실행을 진행하는 메서드
    // 이메서드를 호출해야 AOP가 적용됨

    // @Before : jp.proceed()를 호출하지 않는다.
    // @After : jp.proceed()를 호출
    // @Around : jp.proceed()를 호출해서 AOP 적용된 메서드 실행전과 후의 작업을
    // 처리할 수 있다.

    @Around("execution(* kr.co.mannam.admin.member.controller.*Controller.*(..))")

    public Object validationCheck(ProceedingJoinPoint jp) throws Throwable {
        // 실행중인 메서드의 매개변수 목록을 리턴
        // postDTO, bindingResult, session
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        // 어떤 필드(변수)에 에러가 있는지 해당메시지를 맵에 저장
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }

                    return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }

            }
        } // for
        return  jp.proceed(); // 클라이언트가 호출한 메서드가 실행
    }
}
