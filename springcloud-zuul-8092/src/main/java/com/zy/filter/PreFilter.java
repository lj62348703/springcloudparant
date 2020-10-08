package com.zy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class PreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        //设置过滤类型
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //设置过过滤器优先级
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //如果是登陆页面不需要过滤
        if (request.getRequestURI().contains("/users/user/login")) {
            return false;
        }
        //是否需要过滤
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        // 鉴权
        if ("/users/user/list".equals(request.getRequestURI())) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }
        System.out.println("===================");
        // 参数校验
        if (StringUtils.isEmpty(request.getParameter("token"))) {
            //返回错误信息
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);

        }
        return null;
    }
}
