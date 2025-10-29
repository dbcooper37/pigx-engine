package com.pigx.engine.oss.specification.arguments.object;

import com.pigx.engine.oss.specification.arguments.base.PutObjectBaseArguments;

import java.io.InputStream;


public class PutObjectArguments extends PutObjectBaseArguments {

    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
