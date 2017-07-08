package com.jfinal.qyweixin.sdk.jfinal;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 从请求中解析 标识Key 并导出 agentId。
 * 开发者可自行实现此接口，并在 JFinalConfig.configInterceptor 或在 JFinalConfig.afterJFinalStart等位置全局注入。
 *
 */
public interface AgentIdParser {

    String getAgentId(Invocation inv);

    String getAgentId(Controller ctl);


    /**
     * 默认corpId解析器，根据设置的标识Key名称，从请求parameterMap中直接取agentId值
     *
     * 默认标识Key名称为"agentId"
     */
    class DefaultParameterAgentIdParser implements AgentIdParser {
        private static final String DEFAULT_AGENT_ID_KEY = "agentId";

        private final String agentIdKey;

        public DefaultParameterAgentIdParser() {
            this.agentIdKey = DEFAULT_AGENT_ID_KEY;
        }

        public DefaultParameterAgentIdParser(String agentIdKey) {
            this.agentIdKey = agentIdKey;
        }

        @Override
        public String getAgentId(Invocation inv) {
            return getAgentId(inv.getController());
        }

        @Override
        public String getAgentId(Controller ctl) {
            return ctl.getPara(agentIdKey);
        }
    }
}
