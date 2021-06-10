package handler;

import config.RobotConfig;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.jetbrains.annotations.NotNull;
import service.RobotService;

public class FriendEventHandler extends SimpleListenerHost {

    private static FriendMessageEvent event;

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        exception.printStackTrace();
    }

    @EventHandler
    public ListeningStatus onMessage(@NotNull FriendMessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理

        RobotService robotService = new RobotService();
        FriendEventHandler.event = event;

        // 接收消息
        String msgString = event.getMessage().contentToString();

        if (msgString.contains("codex")) {
            event.getSender().sendMessage(RobotConfig.FRIEND_WELCOME_STR);
        } else {
            // 聊天
            String ans = robotService.chat(msgString, event.getSender().getId());
            event.getSender().sendMessage(ans);
        }
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }
}
