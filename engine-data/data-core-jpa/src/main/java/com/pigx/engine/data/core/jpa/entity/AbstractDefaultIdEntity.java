package com.pigx.engine.data.core.jpa.entity;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p> Description : 默认使用 id 作为数据库主键的实体基础类  </p>
 * <p>
 * 系统中实体主键均在实体中自由定义，默认规范为 "实体名 + Id" 形式。
 * 如果不喜欢这种方式，想直接使用 "id" 作为主键字段名，可以直接继承本类。
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 17:43
 */
@MappedSuperclass
public abstract class AbstractDefaultIdEntity extends AbstractSysEntity {

    @Schema(name = "ID", title = "实体主键")
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .addValue(super.toString())
                .toString();
    }
}
