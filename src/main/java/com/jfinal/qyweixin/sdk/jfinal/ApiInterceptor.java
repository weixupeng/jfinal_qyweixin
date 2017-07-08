package com.jfinal.qyweixin.sdk.jfinal;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.qyweixin.sdk.api.ApiConfigKit;


/**
 * ApiController 为 ApiController 绑定 ApiConfig 对象到当前线程，
 * 以便在后续的操作中可以使用 ApiConfigKit.getApiConfig() 获取到该对象
 */
public class ApiInterceptor implements Interceptor {
    private static AgentIdParser _parser = new AgentIdParser.DefaultParameterAgentIdParser();

    public static void setAgentIdParser(AgentIdParser parser) {
    	System.out.println("setAgentIdParser");
        _parser = parser;
    }

    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        if (!(controller instanceof ApiController))
            throw new RuntimeException("控制器需要继承 ApiController");

        try {
            String agentId = _parser.getAgentId(controller);
            // 将 agentId 与当前线程绑定，以便在后续操作中方便获取ApiConfig对象： ApiConfigKit.getApiConfig();
            ApiConfigKit.setThreadLocalAgentId(agentId);
            inv.invoke();
        } finally {
            ApiConfigKit.removeThreadLocalAgentId();
        }
    }
}

