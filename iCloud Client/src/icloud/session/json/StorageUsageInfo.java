package icloud.session.json;

public class StorageUsageInfo {
	private String totalStorageInBytes;
	private String commerceStorageInBytes;
	private String usedStorageInBytes;
	private String compStorageInBytes;

	public String getTotalStorageInBytes() {
		return totalStorageInBytes;
	}

	public int getCommerceStorageInBytes() {
		return Integer.parseInt(commerceStorageInBytes);
	}

	public int getUsedStorageInBytes() {
		return Integer.parseInt(usedStorageInBytes);
	}

	public int getCompStorageInBytes() {
		return Integer.parseInt(compStorageInBytes);
	}

}
