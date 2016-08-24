package springtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(value = {"/springtest/b.properties","/springtest/a.properties"})
public class Datasource {
	@Value("${a.a}")
	private String a;
	@Value("${a.b}")
	private String b;
	@Value("${a.c}")
	private int c;
	@Value("${a.e}")
	private boolean e;
	
	@Value("${b.a}")
	private String ba;
	@Value("${b.b}")
	private String bb;
	@Value("${b.c}")
	private int bc;
	@Value("${b.e}")
	private boolean be;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer  getProperty() {
		PropertySourcesPlaceholderConfigurer  c = new PropertySourcesPlaceholderConfigurer ();

		return c;
	}

	@Bean(name="a1")
	public MyDataSource getMyDataSource() {
		MyDataSource source = new MyDataSource();
		source.setA(a);
		source.setB(b);
		source.setC(c);
		source.setE(e);
		return source;
	}
	@Bean(name="a2")
	public MyDataSource getMyDataSource2() {
		MyDataSource source = new MyDataSource();
		source.setA(ba);
		source.setB(bb);
		source.setC(bc);
		source.setE(be);
		return source;
	}
}
