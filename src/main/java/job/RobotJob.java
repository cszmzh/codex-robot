package job;

import config.IDConfig;
import config.RobotConfig;
import lombok.SneakyThrows;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import service.RobotService;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class RobotJob implements Job {

    Iterator<Long> iterator = IDConfig.groupIdSet.iterator();

    RobotService robotService = new RobotService();

    Bot bot = Bot.getInstance(RobotConfig.QQ_ACCOUNT);

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) {

        // 获取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // 刷新新闻图片，获取名人名言
        robotService.getZakerInternetNews(10000);
        String goodSaying = robotService.getGoodSaying();

        if (hour == 7) {
            while (iterator.hasNext()) {
                long groupId = iterator.next();
                bot.getGroup(groupId).sendMessage("⭐温馨提示⭐\n① 已经早上" + hour + "点整啦，" +
                        "一日之计在于晨，记得吃早餐哦！\n② 可以发送 \"" + RobotConfig.SERVER_NAME +
                        "\" 获取我的使用方法，我会一直陪伴在你身旁！\n" +
                        "③【名言分享】" + goodSaying);
                sendInternetNews(groupId);
            }
        }
    }

    public void sendInternetNews(long groupId) {
        // 发送新闻
        String savePath = RobotConfig.IMAGE_LOCAL_DIR + "InternetNews.png";
        File pic = new File(savePath);
        final Image image = Bot.getInstance(RobotConfig.QQ_ACCOUNT).getGroup(groupId).uploadImage(ExternalResource.create(pic));
        bot.getGroup(groupId).sendMessage(image);
    }
}
