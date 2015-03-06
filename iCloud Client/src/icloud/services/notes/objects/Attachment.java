package icloud.services.notes.objects;

import common.CommonLogic;

public class Attachment {
    private String attachmentId = CommonLogic.generateUUID();
    private String contentType = null;
    private String contentId = null;
    private int size = 0;

    public int getSize() {
        return this.size;
    }

    public String getContentId() {
        return this.contentId;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public String toString() {
        return "Attachments [\n\tattachmentId=" + this.attachmentId + "\n\t" + "contentType=" + this.contentType + "\n\t" + "contentId=" + this.contentId + "\n\t" + "size=" + this.size + "]";
    }
}

