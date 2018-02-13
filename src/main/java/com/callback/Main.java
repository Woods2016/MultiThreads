package com.callback;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2017/7/10
 */
public class Main {
    public static void main(String[] args) {
        Answerer answerer = new Answerer();
        Quizzer quizzer = new Quizzer(answerer);
        quizzer.ask("这是我的问题");
    }
}
