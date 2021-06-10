package utils;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;

public class BotUtil {

    /**
     * 1.发送本地图片
     *
     * @param contact    联系对象
     * @param path       图片相对路径
     * @param recallTime 撤回时间，0为不撤回
     */
    public static void sendLocalImg(Contact contact, String path, long recallTime) {
        contact.getBot().getLogger().info("【发送图片】--->" + contact.getId() + ", " + path);
        File pic = new File(path);
        final Image image = contact.uploadImage(ExternalResource.create(pic));
        if (recallTime != 0) {
            contact.sendMessage(image).recallIn(recallTime);
        } else {
            contact.sendMessage(image);
        }
    }
}
