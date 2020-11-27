package com.fei.essayjoke;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Human human = new Human();
        Object proxyInstance = Proxy.newProxyInstance(Human.class.getClassLoader(),
                Human.class.getInterfaces(), new HumanInvocationHandler(human));
        ((IBank)proxyInstance).apply("123");
    }

    private static class HumanInvocationHandler implements InvocationHandler {


        private final Human human;

        public HumanInvocationHandler(Human human) {
            this.human = human;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("处理一些事情");
            Object invoke = method.invoke(human, args);
            System.out.println("完毕");
            return invoke;
        }
    }
}