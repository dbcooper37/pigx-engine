package com.pigx.engine.oss.specification.arguments.object;

import com.pigx.engine.oss.specification.arguments.base.PutObjectBaseArguments;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;


public class UploadObjectArguments extends PutObjectBaseArguments {

    @Schema(name = "文件名", description = "服务器端完整的文件名，包括绝对路径和名称")
    @NotEmpty(message = "文件名不能为空")
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
