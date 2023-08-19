package ru.zahv.alex.socialnetwork.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.zahv.alex.socialnetwork.business.service.AuthService;
import ru.zahv.alex.socialnetwork.utils.SecurityContextHolder;

import java.security.Principal;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    private final AuthService authService;
    public static final String WS_NEW_POST_URL = "/post/feed/posted";
    public static final String WS_URL = "/ws";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(WS_NEW_POST_URL);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WS_URL);
        registry.addEndpoint(WS_URL).setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> userMessage, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(userMessage, StompHeaderAccessor.class);
                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authHeader = ofNullable(accessor.getNativeHeader("Authorization"))
                            .map(headers -> headers.get(0))
                            .orElse(StringUtils.EMPTY);
                    authService.checkIsAuthenticated(authHeader);

                    String userId = SecurityContextHolder.Companion.getCurrentUser();
                    Principal principal = () -> userId;
                    Authentication user = new UsernamePasswordAuthenticationToken(principal, null);
                    accessor.setUser(user);
                }
                return userMessage;
            }
        });
    }
}
