package spring.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogAroundAdvice implements MethodInterceptor{
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		long start= System.currentTimeMillis();
		
		//앞에서 했던 invoke메서드와 동일 
		Object result = invocation.proceed();

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();

		String message = (end-start)+"ms 시간이 걸렸습니다.";
		System.out.println(message);

		return result;
	}
}
