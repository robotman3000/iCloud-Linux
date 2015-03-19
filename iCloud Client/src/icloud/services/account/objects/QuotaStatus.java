package icloud.services.account.objects;

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

	public boolean isHaveMaxQuotaTier() {
		return haveMaxQuotaTier;
	}
}
