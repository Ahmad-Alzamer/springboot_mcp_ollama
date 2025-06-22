package com.alzamer.ahmad.springboot_rest_mcp.actuator.info;


import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class McpInfoContributor implements InfoContributor {
    private final List<McpSyncClient> mcpSyncClients;

    public McpInfoContributor(List<McpSyncClient> mcpSyncClients) {
        this.mcpSyncClients = mcpSyncClients;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("mcpSyncClients", mcpSyncClients.stream().map(c -> McpSyncClientInfo.builder().serverName(c.getServerInfo().name()).serverVersion(c.getServerInfo().version()).capabilities(c.listTools().tools().stream().map(McpSchema.Tool::name).toList()).build()));

    }

    @Data
    @Builder
    public static class McpSyncClientInfo {
        private final String serverName;
        private final String serverVersion;
        private final List<String> capabilities;
    }
}
