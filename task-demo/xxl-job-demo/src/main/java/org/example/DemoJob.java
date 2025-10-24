package org.example;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class DemoJob {
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        System.out.println("XXL-Job execute" + System.currentTimeMillis());
    }

    @XxlJob("hello")
    public void hello() throws Exception {
        String param = XxlJobHelper.getJobParam();
        System.out.println("hello " + param);
    }
}
