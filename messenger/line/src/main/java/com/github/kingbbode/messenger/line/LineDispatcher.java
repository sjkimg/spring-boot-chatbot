package com.github.kingbbode.messenger.line;

import com.github.kingbbode.chatbot.core.brain.DispatcherBrain;
import com.github.kingbbode.chatbot.core.common.interfaces.Dispatcher;
import com.github.kingbbode.chatbot.core.common.request.BrainRequest;
import com.github.kingbbode.chatbot.core.common.result.BrainResult;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.Optional;

@Slf4j
public class LineDispatcher implements InitializingBean {

    private DispatcherBrain dispatcherBrain;

    public LineDispatcher(DispatcherBrain dispatcherBrain) {
        this.dispatcherBrain = dispatcherBrain;
    }

    public String dispatch(MessageEvent<TextMessageContent> event) {
        BrainRequest brainRequest = BrainRequest.builder()
                .user(event.getSource().getUserId())
                .room(event.getReplyToken().trim())
                .content(event.getMessage().getText())
                .messageNo(event.getMessage().getId())
                .build();
        BrainResult brainResult = dispatcherBrain.execute(brainRequest);

        return Optional.ofNullable(brainResult).map(BrainResult::getMessage).orElse("");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[BOT] Registered LineDispatcher.");
    }
}
