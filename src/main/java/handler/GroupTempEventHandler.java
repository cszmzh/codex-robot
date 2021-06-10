package handler;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;
import org.jetbrains.annotations.NotNull;

public class GroupTempEventHandler extends SimpleListenerHost {

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        exception.printStackTrace();
    }

    @EventHandler
    public ListeningStatus onMessage(@NotNull GroupTempMessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理

        event.getSender().sendMessage("请在群内发送指令，或者添加我为好友后再发送指令哦！");

        return ListeningStatus.LISTENING; // 表示继续监听事件
    }
}
