package com.ants.mini.programs.config.sentinel;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sentinel Configure
 *
 * @author Yueyang
 * @create 2020-11-18 17:20
 **/
public class SentinelConfig extends ApplicationEvent {

    private ApplicationContext applicationContext;

    public SentinelConfig(Object source) {
        super(source);
    }

    /**
     * 由于使用注解的方式，所以规则方法不允许为Public
     * <p>
     * 设定资源 pre-created-order 每秒只允许通过20个请求
     */
    @TransactionalEventListener
    public static void initFlowRules() {
        // 设置限流
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        // 资源名，资源名是限流规则的作用对象
        flowRule.setResource("pre-created-order");
        // 限流阈值类型，QPS 模式（1）或并发线程数模式（0）
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        // 限流阈值
        flowRule.setCount(20);
        // 限流冷启动,主要处理爆发式增长,长期处于波谷的服务器,突然暴增消耗,会干死服务器
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP);
        // limitApp 流控针对的调用来源 default:代表不区分调用来源
        // strategy 调用关系限流策略：直接、链路、关联 default:根据资源本身（直接）
        // controlBehavior 流控效果（直接拒绝/WarmUp/匀速+排队等待），不支持按调用关系限流 default:直接拒绝
        // clusterMode	是否集群限流	default:否
        flowRules.add(flowRule);
        FlowRuleManager.loadRules(flowRules);
    }

    public static void initDegradeRules() {
        // 设置熔断
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("pre-created-order");
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        degradeRule.setCount(0.5);
        degradeRule.setTimeWindow(10);
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);
    }

    static void initSystemRule() {
        List<SystemRule> rules = new ArrayList<SystemRule>();
        SystemRule rule = new SystemRule();
        // max load is 3
        rule.setHighestSystemLoad(3.0);
        // max cpu usage is 60%
        rule.setHighestCpuUsage(0.6);
        // max avg rt of all request is 10 ms
        rule.setAvgRt(10);
        // max total qps is 20
        rule.setQps(20);
        // max parallel working thread is 10
        rule.setMaxThread(10);

        rules.add(rule);
        SystemRuleManager.loadRules(Collections.singletonList(rule));
    }
}