package com.gioorgi.jdk8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Component;

//@Component	
//@ComponentScan
public class LambdaGuyExample {
	
	public static final long JvmStartupTime_ms=System.currentTimeMillis();
	@SuppressWarnings("serial")
	public static void main(String[] args) throws Exception { 
		
		
		// The static timer enable us to do sequential timing:
		
		ThreadLocal<Long> lastRecordedTime= 
				ThreadLocal.withInitial(()->JvmStartupTime_ms);
		
		java.util.function.Consumer<Object> log= (msg) -> {
			
			Thread currentThread = Thread.currentThread();
			Long startTime=lastRecordedTime.get();
			String n=currentThread.getName();
			System.err.println(msg+ "\t("+n+") "+
					((startTime!=JvmStartupTime_ms)?
					"["+(System.currentTimeMillis()-startTime)+"ms ]":
					" [??] "));
			lastRecordedTime.set(System.currentTimeMillis());
		};
		
		// Log flusher is able to  pass-tru logs.
		java.util.function.UnaryOperator<Object> loGate=(x) -> {
			log.accept("FLUSHER:"+x);
			return x;
		};
		
		
		java.util.function.Function<Object, Object> aliasGate=loGate;
		
		log.accept("Pool guys:"+ForkJoinPool.commonPool().getPoolSize());
		 
	    System.err.println(""+log);
	    log.accept("Log gate:"+aliasGate);
	    
	    

		
		
		java.util.function.BiFunction<String, String, Integer> bf=
				 (String first, String second) -> Integer.compare(first.length(), second.length());
		
		/* When you want to do something with lambda expressions, you still 
		 * want to keep the purpose of the expression in mind, 
		 * and have a specific functional interface for it.
		 * See http://www.drdobbs.com/jvm/lambda-expressions-in-java-8/240166764?pgno=2
		 * So you cannto assign bf and c, sorry
		 */
				 
									 
				 
		
		Runnable r2 = () -> System.out.println("Hello world two!");
		r2.run();
	
		Random r= new Random(23);
		
		// Old Java 5 syntax: but beware: you are creating a inner class subclass of ArrayList!
		List<Integer> l=new ArrayList<Integer>() {{
			add(r.nextInt(5));
			add(r.nextInt(5));
			add(r.nextInt(5));
		}};
		
		for(int i=1; i<=10;i++) {			
			l.add(r.nextInt(5));
			l.add(r.nextInt(5));
			l.add(r.nextInt(5));
		}
		
		
		
		
		log.accept("All elements:"+l.parallelStream().count());
		
		
		log.accept("Distinct elements:"+l.parallelStream().distinct().count());
		
		
		// Look the difference between
		l.parallelStream().distinct().forEach(log);
		// and default:
		l.stream().distinct().forEach(log);
		
		l.parallelStream().distinct().sorted().sequential().filter((x) -> x >= 4)
				.forEach((x) -> {
					log.accept("Distinct elem Sorted and filtered:" + x);
				});


		
		
		// The chaining of logFlusher is done via map
		l.parallelStream().filter((x) -> x <= 3).map(loGate).count();
		IntStream is=l.stream().mapToInt((x) -> (x)).parallel().distinct();
		
			
		
		// Collectors.groupingByConcurrent(classifier)
	
//		ConcurrentMap<Person.Sex, List<Person>> byGender =
//			    roster.parallelStream().collect(
//			        Collectors.groupingByConcurrent(Person::getGender));
		// (4+0)/2 mean is 2
		log.accept("Mean:"+is.average());
		
		
		// This accoumulator can be asfter then atomiclong
		java.util.concurrent.atomic.LongAccumulator acc;
		
		
		// Recursive guy: but does not work as expected:
//		UnaryOperator<Integer> factorial = i -> { return i == 0 ? 1 : i * factorial.apply( i - 1 ); };
		
		

		
		
		// Try to enlarge pool
		List<Integer> superHuge=new ArrayList<Integer>();		
	
				
		
		l.forEach(superHuge::add);
		// 3x
		l.forEach((x)-> { superHuge.add(x); superHuge.add(x); superHuge.add(x); }); 
		
		
		
		
		// See java.util.concurrent.ForkJoinPool.makeCommonPool()
		// for rule used to do the common pool. Parallelism is ((number of processors)-1)
		// Use the system property 'java.util.concurrent.ForkJoinPool.common.parallelism'
		// to trick the pool
		
		log.accept("Ready process #elements:"+superHuge.size() );
		log.accept("Super Huge AVERAGE:"
				+ superHuge.parallelStream().peek(
						(e) -> {
							log.accept("No distinct:" + e);
						}).parallel().distinct().peek(
								(e) -> {
									log.accept("DISTINCT:" + e);
								}).parallel().mapToInt((x) -> (x)).average());
		
		log.accept("Parallel AVERAGE:"+superHuge.parallelStream().mapToInt((x) -> (x)).average());
		log.accept("Serial AVERAGE:"+superHuge.stream().mapToInt((x) -> (x)).average());
		log.accept("Pool guys:" + ForkJoinPool.commonPool().getPoolSize());

	    //Comparator<String> c = (String first, String second) -> Integer.compare(first.length(), second.length());
	    
		
	    parallelTest(log);
		
	    
	    // actorTest(log);
	    
	    
	}


//	
//	public static void  actor1() {
//		
//	}
//
//	
//	private static void actorTest(Consumer<Object> log) {
//		// Actor, yea
//	    // Actor are registered runnable.
//	    Function<Runnable, Integer> spawn= (r) ->
//	    	0;
//	    int pid=spawn.apply(LambdaGuyExample::actor1);
//	}





	private static void parallelTest(java.util.function.Consumer<Object> log) {
		Comparator<String> comparatorLogger =(String first, String second) -> {
	    	comparatorExternalCounter++;
	    	//log.accept(first+" "+second);
	    	if( comparatorExternalCounter <=5 || (comparatorExternalCounter % 3000 == 1) ) {
				log.accept("Cmp" + first+" "+second+ " {"+comparatorExternalCounter+"}");
			}
	    	return first.compareToIgnoreCase(second);
	    };
	    
	    // PArallel sort will no bother you with threads if you have less the 8K elements.
	    log.accept("Ready to sort parallel test: MIN_ARRAY_SORT_GRAN =" + (1 << 13));
	    comparatorExternalCounter=0;
	    
		List<String> sortTest=new ArrayList<String>();		
		IntStream.range(1, 3*(1 << 13)).boxed().map( i -> ""+i ).forEach(sortTest::add);
		
		String[] hugestrings = sortTest.toArray(new String[] {});
		Arrays.parallelSort(hugestrings, comparatorLogger);
		//Arrays.stream(hugestrings).distinct().forEach(log);						
		log.accept("Parallel Test Finished");
	}
	
	
	
	static int comparatorExternalCounter=0;
	
	
//	public interface LoggableStream<T> extends Stream<T>{
//		default  LoggableStream<T> log(){
//			this.sequential().forEach((elem)->{
//				Objects.requireNonNull(elem);
//				String n=Thread.currentThread().getName();
//				System.err.println(elem+ "\t("+n+")"); 
//			});
//			return this;
//		}
//	}
	
}
