package com.pigx.engine.logic.message.listener;

import com.pigx.engine.logic.message.entity.DialogueDetail;
import com.pigx.engine.logic.message.service.DialogueDetailService;
import com.pigx.engine.message.core.domain.DialogueMessage;
import com.pigx.engine.message.core.event.SendDialogueMessageEvent;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/listener/DialogueMessageListener.class */
public class DialogueMessageListener implements ApplicationListener<SendDialogueMessageEvent> {
    private final DialogueDetailService dialogueDetailService;

    public DialogueMessageListener(DialogueDetailService dialogueDetailService) {
        this.dialogueDetailService = dialogueDetailService;
    }

    public void onApplicationEvent(SendDialogueMessageEvent event) {
        if (ObjectUtils.isNotEmpty(event)) {
            DialogueMessage dialogueMessage = event.getData();
            if (ObjectUtils.isNotEmpty(dialogueMessage)) {
                DialogueDetail dialogueDetail = convertDialogueMessageToDialogueDetail(dialogueMessage);
                this.dialogueDetailService.save(dialogueDetail);
            }
        }
    }

    private DialogueDetail convertDialogueMessageToDialogueDetail(DialogueMessage dialogueMessage) {
        DialogueDetail dialogueDetail = new DialogueDetail();
        dialogueDetail.setDetailId(dialogueMessage.getDetailId());
        dialogueDetail.setReceiverId(dialogueMessage.getReceiverId());
        dialogueDetail.setReceiverName(dialogueMessage.getReceiverName());
        dialogueDetail.setReceiverAvatar(dialogueMessage.getReceiverAvatar());
        dialogueDetail.setContent(dialogueMessage.getContent());
        dialogueDetail.setDialogueId(dialogueMessage.getDialogueId());
        dialogueDetail.setSenderId(dialogueMessage.getSenderId());
        dialogueDetail.setSenderName(dialogueMessage.getSenderName());
        dialogueDetail.setSenderAvatar(dialogueMessage.getSenderAvatar());
        return dialogueDetail;
    }
}
