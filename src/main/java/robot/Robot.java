package robot;

import config.RobotConfig;
import handler.FriendEventHandler;
import handler.GroupEventHandler;
import handler.GroupTempEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.quartz.SchedulerException;
import service.TimeService;

import java.io.File;

/**
 * 这是一个QQ机器人
 * 基于 mirai-core 修改的 maven 项目模板
 *
 * @author https://github.com/BA-NANA
 */
public class Robot {

    public static void main(String[] args) throws SchedulerException {

        Bot bot = BotFactory.INSTANCE.newBot(RobotConfig.QQ_ACCOUNT, RobotConfig.QQ_PASS, new BotConfiguration() {
            {
                // 保存设备信息到文件
                fileBasedDeviceInfo("deviceInfo.json");
                // 指定登陆方式
                setProtocol(MiraiProtocol.ANDROID_PHONE);
                // 指定输出日志路径
                redirectBotLogToFile(new File("logs/" + RobotConfig.QQ_ACCOUNT));
            }
        });

        // 登录
        bot.login();

        // 输出好友
        bot.getFriends().forEach(friend -> System.out.println(friend.getId() + ":" + friend.getNick()));

        // 注册监听事件
        bot.getEventChannel().registerListenerHost(new GroupEventHandler());
        bot.getEventChannel().registerListenerHost(new FriendEventHandler());
        bot.getEventChannel().registerListenerHost(new GroupTempEventHandler());

        // 加载定时任务
        new TimeService().Mission();

        // 阻塞当前线程直到 bot 离线
        bot.join();
    }
}
