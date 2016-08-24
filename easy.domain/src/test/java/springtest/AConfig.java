package springtest;

import org.springframework.beans.factory.annotation.Value;

public class AConfig {
	
	@Value("${a}")
	public String a;
	@Value("${b}")
	public String b;
	@Value("${c}")
	public int c;
	@Value("${e}")
	public boolean e;
}
