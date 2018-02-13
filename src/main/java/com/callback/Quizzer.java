package com.callback;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2017/7/10
 */
public class Quizzer implements CallBack {

    private Answerer answerer;

    public Quizzer(Answerer answerer) {
        this.answerer = answerer;
    }

    public void ask(String question) {
        System.out.println("开始提问");
        new Thread(() ->
                answerer.executeMsg(Quizzer.this, question)).start();

        System.out.println("提问完毕，处理其他事情");
    }

    @Override
    public void solve(String param) {
        System.out.println("回调结果是：" + param);
    }
}
