package com.fruit.template.email.dto;

/**
 * 抄送人dto
 */
public class MailSjrDto {
    private String id;

    private String mailId;

    private String maillAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getMaillAddress() {
        return maillAddress;
    }

    public void setMaillAddress(String maillAddress) {
        this.maillAddress = maillAddress;
    }
}
