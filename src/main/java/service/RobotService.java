package service;

import config.RobotConfig;
import entity.Weather;

import java.io.IOException;

public class RobotService {

    private final NetWorkService netWorkService = new NetWorkService();

    private final String robotName = RobotConfig.SERVER_NAME;

    /**
     * 1.获取西青区温度
     *
     * @return 温度情况
     * @throws IOException IO异常
     */
    public String getWeather() throws IOException {
        Weather weather = netWorkService.getWeather();
        String ans = "⭐" + robotName + "天气查询服务⭐\n" +
                "西青区当前气温: " + weather.getTemNow() + "℃\n" +
                "全天气温: " + weather.getTem1() + "℃ ~ " + weather.getTem2() + "℃\n" +
                weather.getStateDetailed() + ", " + weather.getWindState() + "\n" +
                "湿度: " + weather.getHumidity();
        return ans;
    }

    /**
     * 2.获取欢迎语句
     *
     * @return 欢迎语句
     */
    public String getWelcome() {
        String ans = RobotConfig.GROUP_WELCOME_STR;
        return ans;
    }

    /**
     * 3.聊天服务
     *
     * @param keywords 关键字
     * @param sender   发送者
     * @return 聊天返回内容
     * @throws IOException IO异常
     */
    public String chat(String keywords, long sender) throws IOException {
        String ans = netWorkService.chat(keywords, sender);
        return ans;
    }

    /**
     * 4.515code文章搜索服务
     *
     * @param keywords 关键字
     * @return 返回内容
     * @throws IOException IO异常
     */
    public String getBlogArticle(String keywords) throws IOException {
        String ans = netWorkService.getBlogArticle(keywords);
        return ans;
    }

    /**
     * 5.获取名人名言
     *
     * @return 名人名言
     * @throws IOException IO异常
     */
    public String getGoodSaying() throws IOException {
        String ans = netWorkService.getGoodSaying();
        return ans;
    }

    /**
     * 6.获取热点新闻图片
     *
     * @param qqId QQ号
     * @return 相对存储路径
     * @throws IOException IO异常
     */
    public String getZakerHotNews(long qqId) throws IOException {
        String ans = netWorkService.getZakerHotNews(qqId);
        return ans;
    }

    /**
     * 7.获取互联网新闻图片
     *
     * @param qqId QQ号
     * @return 相对存储路径
     * @throws IOException IO异常
     */
    public String getZakerInternetNews(long qqId) throws IOException {
        String ans = netWorkService.getZakerInternetNews(qqId);
        return ans;
    }

    /**
     * 8.获取CET查询结果
     *
     * @param mode   模式 "4"或者"6"
     * @param number 准考证号
     * @param name   名字
     * @return 结果
     * @throws IOException IO异常
     */
    public String getCET(String mode, String number, String name) throws IOException {
        String ans = netWorkService.getCET(mode, number, name);
        return ans;
    }

    /**
     * 9.获取彩虹屁
     *
     * @return 夸夸语句
     * @throws IOException IO异常
     */
    public String getLove() throws IOException {
        String ans = netWorkService.getLove();
        return ans;
    }

    /**
     * 10.英语单词查询
     *
     * @param key 关键字
     * @return
     * @throws IOException
     */
    public String getEnWord(String key) throws IOException {
        String ans = netWorkService.getBczWord(key);
        return ans;
    }
}
