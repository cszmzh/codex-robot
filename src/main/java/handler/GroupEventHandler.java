package handler;

import config.IDConfig;
import config.RobotConfig;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.QuoteReply;
import org.jetbrains.annotations.NotNull;
import service.RobotService;
import utils.BotUtil;

import java.io.IOException;

public class GroupEventHandler extends SimpleListenerHost {

    private static GroupMessageEvent event;

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        exception.printStackTrace();
    }

    @EventHandler
    public ListeningStatus onMessage(@NotNull GroupMessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理

        RobotService robotService = new RobotService();
        GroupEventHandler.event = event;

        if (IDConfig.groupIdSet.contains(event.getGroup().getId())) {

            // 接收消息
            String msgString = event.getMessage().contentToString();

            if (msgString.contains("天气")) {
                // 发送天气信息
                String ans = robotService.getWeather();
                event.getGroup().sendMessage(ans);
            } else if (msgString.equals(RobotConfig.SERVER_NAME)) {
                // 发送欢迎语
                String ans = robotService.getWelcome();
                event.getGroup().sendMessage(ans);
            } else if (msgString.contains("博客")) {
                // 发送博客链接
                event.getGroup().sendMessage("想看博客？欢迎访问 https://www.515code.com/");
            } else if (msgString.matches(".*马哥(强|太强|).*")) {
                event.getGroup().sendMessage("那我马哥肯定强啊！\n" +
                        "马哥1080p海报：https://www.515code.com/photos/wallpaper/#group\n" +
                        "马哥的博客：https://aubreyii.github.io/");
            } else if (msgString.matches(".*铭大佬(太强|强|).*")) {
                event.getGroup().sendMessage("他太菜了，我马哥才是最强的！");
            } else if (msgString.matches(".*牛哥(太强|强|).*")) {
                event.getGroup().sendMessage("哞哞强啊！！！115的守护神！！！\n" +
                        "牛哥的博客：https://epsilonzy.github.io/");
            } else if (msgString.matches(".*黄老板(强|太强|).*")) {
                event.getGroup().sendMessage("我黄老板跟你谈这些的吗！强！学就完事了！！！\n" +
                        "黄老板的博客：https://gality.cn/");
            } else if (msgString.matches(".*贤哥(强|太强|).*")) {
                event.getGroup().sendMessage("贤哥猴赛雷！贤哥真滴强！\n" +
                        "贤哥的博客：http://minions.sipc115.com/");
            } else if (msgString.matches(".*商公子(太强|强|).*")) {
                event.getGroup().sendMessage("我商公子又帅又强，猴赛雷！！！");
            } else if (msgString.contains("强")) {
                event.getGroup().sendMessage("强！大家都强！张钊铭最菜！");
            } else if (msgString.contains("防下塔")) {
                // 引用回复示例
                String ans = robotService.getLove();
                final QuoteReply quote = new QuoteReply(event.getSource());
                event.getGroup().sendMessage(quote.plus(ans));
            } else if (msgString.matches(".*名(人名|)言.*")) {
                // 发送名人名言
                String ans = robotService.getGoodSaying();
                event.getGroup().sendMessage(ans);
            } else if (msgString.startsWith(RobotConfig.SERVER_NAME)) {
                // 搜索515code文章
                String ans = robotService.getBlogArticle(msgString.replace(RobotConfig.SERVER_NAME, ""));
                event.getGroup().sendMessage(ans);
            } else if (msgString.contains("热点")) {
                // 热点新闻
                robotService.getZakerHotNews(event.getSender().getId());
                // 发送图片
                String path = RobotConfig.IMAGE_LOCAL_DIR + "HotNews.png";
                BotUtil.sendLocalImg(event.getGroup(), path, 0);
            } else if (msgString.contains("科技")) {
                // 科技新闻
                robotService.getZakerInternetNews(event.getSender().getId());
                // 发送图片
                String path = RobotConfig.IMAGE_LOCAL_DIR + "InternetNews.png";
                BotUtil.sendLocalImg(event.getGroup(), path, 0);
            } else if (msgString.contains("cdc")) {
                // 查英语单词
                String keyword = msgString.replace("cdc", "").replace(" ", "");
                String ans = robotService.getEnWord(keyword);
                event.getGroup().sendMessage(ans);
            }

            // 处理@机器人的请求
            event.getMessage().forEach(msg ->
            {
                if (msg instanceof At && ((At) msg).getTarget() == RobotConfig.QQ_ACCOUNT) {
                    try {
                        String ans = robotService.chat(msgString.replace(((At) msg).getDisplay(null), ""),
                                event.getSender().getId());
                        event.getGroup().sendMessage(ans);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }
}
