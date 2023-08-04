package kr.co.mannam.domain.config.webchat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebChatConfig implements WebSocketMessageBrokerConfigurer {

    // 웹 소켓 연결을 위한 엔드포인트 설정 및 stomp sub/pub 엔드 포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Stomp 접속 주소 url = /(원하는거 아무거나)
        registry.addEndpoint("/ws-stomp") // /ws-stomp 엔드 포인트 연결
                .withSockJS(); // SocketJS 를 연결, 연결 지원이 안됐을 때 대체하여 연결하기 위해 사용
    }

    // 메시지 공급하는 객체(publisher)와 소비하는 객체(subscriber) 로 분리하여 제공
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // sub, 메시지 받는 개념
        registry.enableSimpleBroker("/sub");

        // pub, 메시지 보내는 개념
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
