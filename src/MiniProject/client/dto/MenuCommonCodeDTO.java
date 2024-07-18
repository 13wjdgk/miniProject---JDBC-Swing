package MiniProject.client.dto;

public class MenuCommonCodeDTO {
	private int codeId;
	private String codeTypeID;
	private String name;

	// 생성자
	public MenuCommonCodeDTO(int codeId, String codeTypeID, String name) {
		this.codeId = codeId;
		this.codeTypeID = codeTypeID;
		this.name = name;
	}

	public MenuCommonCodeDTO() {

	}

	// Getter 메서드
	public int getCodeId() {
		return codeId;
	}

	public String getCodeTypeID() {
		return codeTypeID;
	}

	public String getName() {
		return name;
	}

	// Setter 메서드 (필요에 따라 추가)
	public void setCodeId(int codeId) {
		this.codeId = codeId;
	}

	public void setCodeTypeID(String codeTypeID) {
		this.codeTypeID = codeTypeID;
	}

	public void setName(String name) {
		this.name = name;
	}

	// toString() 메서드 (디버깅 등에 유용)
	@Override
	public String toString() {
		return "MenuCommonCodeDTO{" +
			"codeId=" + codeId +
			", codeTypeID='" + codeTypeID + '\'' +
			", name='" + name + '\'' +
			'}';
	}
}
