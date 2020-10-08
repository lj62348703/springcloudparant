package com.zy.filter;

import com.zy.util.TokenUtil;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicReference;

/**
 * gateway全局权限过滤器
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    Logger logger= LoggerFactory.getLogger( TokenFilter.class );
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = null;

        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String method = serverHttpRequest.getMethodValue();
        String bodyStr = "";
        if ("POST".equals(method)) {
            //从请求里获取Post请求体
            bodyStr = resolveBodyFromRequest(serverHttpRequest);
            System.out.println("bodyStr:"+bodyStr);
            if(bodyStr!= null && bodyStr.contains("&")) {
                //TODO 这里对你获取的参数进行操作，比如 token session 的验证
                StringTokenizer st = new StringTokenizer(bodyStr, "&");
                while (st.hasMoreTokens()) {
                    String tokenStr = st.nextToken();
                    if (tokenStr.contains("token=")) {
                        token = tokenStr.substring(tokenStr.indexOf("token=") + 6);
                        System.out.println("token:" + token);
                    }
                }
            }else if(bodyStr!= null && bodyStr.contains("token=")){
                token = bodyStr.substring(bodyStr.indexOf("token=") + 6);
            }else{
                token = null;
            }
        } else if ("GET".equals(method)) {
            //TODO 得到Get请求的请求参数后，做你想做的事
            token = exchange.getRequest().getQueryParams().getFirst("token");
        }

        //如果是登陆页面不需要过滤
        if (exchange.getRequest().getURI().getPath().contains("/user/login")) {
            return chain.filter(exchange);
        }
        if (token == null || token.isEmpty()) {
            logger.info( "token is empty..." );
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // token参数校验
        if (TokenUtil.verify(token) == null) {
            //返回错误信息
            logger.info( "token error..." );
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }


        if("POST".equals(method)){
            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
            URI uri = serverHttpRequest.getURI();
            ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
            DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
            request = new ServerHttpRequestDecorator(request) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }
            };
            //封装request，传给下一级
            return chain.filter(exchange.mutate().request(request).build());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }

    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

}
