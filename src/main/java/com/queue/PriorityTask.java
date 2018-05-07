package com.queue;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/5/7
 */
@Data
@AllArgsConstructor
public class PriorityTask implements Comparable<PriorityTask> {

    private Integer priority;
    private String name;

    @Override
    public int compareTo(PriorityTask o) {
        return o.getPriority().compareTo(this.priority);
    }

}
