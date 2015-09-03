package icloud.session;

public class QuotaStatus {
	private boolean overQuota;
	private boolean paidQuota;
	private boolean haveMaxQuotaTier;

	public boolean isOverQuota() {
		return overQuota;
	}

	public boolean isPaidQuota() {
		return paidQuota;
	}

	public boolean hasMaxQuotaTier() {
		return haveMaxQuotaTier;
	}
}
