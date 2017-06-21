package easy.domain.base;

public class BrokenRuleException extends RuntimeException {
	private static final long serialVersionUID = 708303351975681548L;

	private String code;

	public BrokenRuleException(String code, String message) {
		super(message);
		
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
