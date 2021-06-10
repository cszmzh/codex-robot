package config;

public class RobotConfig {

    // 配置机器人名字
    public static String SERVER_NAME = "codex";

    // 配置QQ账号
    public static long QQ_ACCOUNT = 123456;

    // 配置QQ密码
    public static String QQ_PASS = "test123456";

    // 配置515code文章查询返回条数
    public static Integer ARTICLE_SIZE = 10;

    // 配置欢迎语
    public static String GROUP_WELCOME_STR = "Hello, 我是" + SERVER_NAME + "!\n" +
            "可爱的我有一些功能：\n" +
            "1.查询文章, 示例: " + SERVER_NAME + " 树莓派\n" +
            "2.夸夸功能, 示例: 马哥强啊\n" +
            "3.查询天气, 示例: 天气\n" +
            "4.随机名言, 示例: 名人名言\n" +
            "5.@我聊天, 示例: @" + SERVER_NAME + "\n" +
            "6.实时热点, 示例: 热点\n" +
            "7.网抑云   , 示例: 防下塔\n" +
            "⭐ 意见反馈: 515code.com/codex ⭐";

    public static String FRIEND_WELCOME_STR = "Hi! 我是" + SERVER_NAME + ", 目前私聊支持下列功能。\n" +
            "1.和我聊天, 示例: 你在干嘛\n";

    // 配置权限不足提示语
    public static String PERMISSION_DENIED = "你不是" + SERVER_NAME + "的专享会员，无法调用此功能哦！";

    // 配置截图存储文件夹
    public static String IMAGE_LOCAL_DIR = "static/img/";

    // 若需要使用Selenium截图，需配置chromedriver路径
    // https://www.515code.com/posts/d2c1m6j5/
    public static String CHROME_DRIVER_DIR_PI = "/usr/bin/chromedriver";
    public static String CHROME_DRIVER_DIR_WIN = "static\\chromedriver\\win\\chromedriver.exe";
}
