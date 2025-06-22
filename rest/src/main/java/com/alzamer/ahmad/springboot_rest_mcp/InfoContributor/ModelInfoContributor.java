package com.alzamer.ahmad.springboot_rest_mcp.InfoContributor;

import com.alzamer.ahmad.springboot_rest_mcp.models.MyOllamaChatModel;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class ModelInfoContributor implements InfoContributor {
    private final MyOllamaChatModel model;

    public ModelInfoContributor(MyOllamaChatModel model) {
        this.model = model;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("ModelInfo",model.getModelInfo());
    }
}
