package old.icloud.services.account.objects;

public class StorageBlockInfo {
	private String displayColor;
	private String usageInBytes;
	private String mediaKey;
	private String displayLabel;

	public String getDisplayColor() {
		return displayColor;
	}

	public int getUsageInBytes() {
		return Integer.parseInt(usageInBytes);
	}

	public String getMediaKey() {
		return mediaKey;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}
}
