package springtest;


public class MyDataSource {
	private String a;
	private String b;
	private int c;
	public String getA() {
		return a;
	}
	public void setA(String a) {
		System.out.println("set a" + a);
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	}
	public boolean isE() {
		return e;
	}
	public void setE(boolean e) {
		this.e = e;
	}
	public boolean e;
}
