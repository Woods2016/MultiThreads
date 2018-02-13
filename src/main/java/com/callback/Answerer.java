package com.callback;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2017/7/10
 */
public class Answerer {

    private Quizzer quizzer;

    public void executeMsg(CallBack callBack, String question)  {
        System.out.println("提问者的问题是：" + question);
        System.out.println("获得了请求，但是有急事要处理");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("事情处理完毕，开始处理提问");
        String result = "回答者的答案";
        callBack.solve(result);
    }
}
