package icloud.services.contacts.objects;

public class Photo {
	private String url = "";
	private String signature = "";
	private boolean isCompany;
	private Crop crop = new Crop();

	public Photo(String photoBase64) {
		// TODO Auto-generated constructor stub
	}

	public Photo() {
		// TODO Auto-generated constructor stub
	}

	public String getUrl() {
		return url;
	}

	public String getSignature() {
		return signature;
	}

	public boolean isCompany() {
		return isCompany;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setCompany(boolean isCompany) {
		this.isCompany = isCompany;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public class Crop {
		private int height;
		private int width;
		private int y;
		private int x;

		public int getHeight() {
			return height;
		}

		public int getWidth() {
			return width;
		}

		public int getY() {
			return y;
		}

		public int getX() {
			return x;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void setX(int x) {
			this.x = x;
		}

	}

	public void getPhoto() {
		// TODO Auto-generated method stub
		
	}

	public void getPhotoAsString() {
		// TODO Auto-generated method stub
		
	}
}
