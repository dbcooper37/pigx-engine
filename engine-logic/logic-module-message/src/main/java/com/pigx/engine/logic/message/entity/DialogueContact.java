package com.pigx.engine.logic.message.entity;

import com.pigx.engine.logic.message.constant.LogicMessageConstants;
import com.pigx.engine.logic.message.domain.AbstractSenderEntity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicMessageConstants.REGION_MESSAGE_DIALOGUE_CONTACT)
@Schema(name = "私信联系")
@Cacheable
@Entity
@Table(name = "msg_dialogue_contact", indexes = {@Index(name = "msg_dialogue_contact_id_idx", columnList = "contact_id"), @Index(name = "msg_dialogue_contact_sid_idx", columnList = "sender_id")})
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/entity/DialogueContact.class */
public class DialogueContact extends AbstractSenderEntity {

    @Id
    @Schema(name = "联系ID")
    @UuidGenerator
    @Column(name = "contact_id", length = 64)
    private String contactId;

    @Column(name = "receiver_id", length = 64)
    @Schema(name = "接收人ID")
    private String receiverId;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicMessageConstants.REGION_MESSAGE_DIALOGUE)
    @ManyToOne
    @Schema(name = "对话ID")
    @JoinColumn(name = "dialogue_id", nullable = false)
    private Dialogue dialogue;

    public String getContactId() {
        return this.contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Dialogue getDialogue() {
        return this.dialogue;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("contactId", this.contactId).add("receiverId", this.receiverId).add("dialogue", this.dialogue).toString();
    }
}
