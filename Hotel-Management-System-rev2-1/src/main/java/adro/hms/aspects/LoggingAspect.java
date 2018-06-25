package adro.hms.aspects;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * This aspect is meant to be used for all of the logging advices within this project
 * @author ADRO
 *
 */
@Aspect
@Component
public class LoggingAspect {

private Logger myLogger = Logger.getLogger(getClass().getName());
	

	@Pointcut("execution(* adro.hms.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* adro.hms.services.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* adro.hms.DAO.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	/**
	 * This advice is executed before the every method in controller, DAO, service packages. 
	 * It will log short version of each method signature and arguments passed to it 
	 * @param theJoinPoint, JoinPoint, contains metadata of the method affected
	 */
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		System.out.println("=====================================================================nnnnnnnnnnnnnnnnnnnnnnn========================================");
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>> in @Before: calling method: " + theMethod);
		
		Object[] args = theJoinPoint.getArgs();
		
		for(Object theArg : args) {
			myLogger.info("====>> argument: " + theArg);
		}
		
	}
	
	/**
	 * This advice is executed after the every method in controller, DAO, service packages 
	 * which returns something. 
	 * It will log short version of method signature and method result.
	 * @param theJoinPoint, JoinPoint, contains metadata of the method affected
	 * @param theResult, Object, result of the method investigated
	 */
	@AfterReturning(
			pointcut = "forAppFlow()",
			returning = "theResult"
			)
	public void after(JoinPoint theJoinPoint, Object theResult) {
		
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>> in @AfterReturning: calling method: " + theMethod);
		
		myLogger.info("====>> result: " + theResult);
		
	}
	
}
