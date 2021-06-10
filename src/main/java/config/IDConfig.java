package config;

import java.util.HashSet;

public class IDConfig {
    // 配置接收消息群号
    public static final HashSet<Long> groupIdSet = new HashSet<Long>() {{
        // SIPC19群
        add(1130234245L);
        // 测试群
        add(287800388L);
    }};

    // 配置VIP成员，享新功能特性
    public static final HashSet<Long> vipIdSet = new HashSet<Long>() {{
        // BA-NANA
        add(798998087L);
        // 黄老板
        add(434400726L);
        // 测试用户
        add(285602877L);
        add(781929659L);
        add(1696411949L);
        add(1014116014L);
    }};
}
