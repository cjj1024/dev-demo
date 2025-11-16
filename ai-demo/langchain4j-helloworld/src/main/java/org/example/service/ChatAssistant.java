package org.example.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ChatAssistant {
    String chat(@MemoryId Long memoryId, @UserMessage String prompt);

    @SystemMessage("你是一个作家")
    @UserMessage("请写一篇关于{{role}}的中文小说, 字数在5000左右, 直接给出文章")
    String writer(@MemoryId Long memoryId, @V("role") String prompt);
}
