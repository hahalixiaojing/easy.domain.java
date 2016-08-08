package easy.domain.test;

import java.time.LocalDateTime;


import org.junit.Test;

public class LocalDateTimeTest {
	@Test
	public void compareTest(){
		LocalDateTime now = LocalDateTime.now();
		
		int result = now.compareTo(now.plusDays(1));
		System.out.println(result);
	}
}
