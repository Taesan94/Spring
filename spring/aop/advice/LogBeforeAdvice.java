package spring.aop.advice;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.springframework.aop.MethodBeforeAdvice;

public class LogBeforeAdvice implements MethodBeforeAdvice{
	
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("[ "+this.getClass() + "] 앞에서 실행될 로직 ");
	}
}
