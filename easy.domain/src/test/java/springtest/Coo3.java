package springtest;

public class Coo3 {
	private int a = 0;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
		
		System.out.println(String.format("this is %s", a));
	}
}
