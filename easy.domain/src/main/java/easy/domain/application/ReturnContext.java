package easy.domain.application;

public class ReturnContext {

	public ReturnContext() {
	}

	public ReturnContext(String systemId, String versionId) {
		this.systemId = systemId;
		this.versionId = versionId;
	}

	public String systemId;
	public String versionId;
}
