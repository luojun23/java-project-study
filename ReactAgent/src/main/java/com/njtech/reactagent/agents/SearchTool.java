package com.njtech.reactagent.agents;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.chat.model.ToolContext;
import java.util.function.BiFunction;
/**
 * @program: java-project-study
 * @description:
 * @author: luojun
 * @create: 2026-07-27 23:58
 **/

public class SearchTool implements BiFunction<String, ToolContext, String>{
    @Override
    public String apply(String query, ToolContext context) {
        // 实现搜索逻辑
        return "搜索结果: " + query;
    }
}
