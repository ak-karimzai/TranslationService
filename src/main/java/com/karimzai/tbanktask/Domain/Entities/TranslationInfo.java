package com.karimzai.tbanktask.Domain.Entities;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class TranslationInfo {
    private UUID id;
    private String userIPAddress;
    private String sourceText;
    private String translatedText;
    private Timestamp createdAt;

    public TranslationInfo() {
    }

    public TranslationInfo(UUID id, String userIPAddress, String sourceText, String translatedText, Timestamp createdAt) {
        this.id = id;
        this.userIPAddress = userIPAddress;
        this.sourceText = sourceText;
        this.translatedText = translatedText;
        this.createdAt = createdAt;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserIPAddress() {
        return userIPAddress;
    }

    public void setUserIPAddress(String userIPAddress) {
        this.userIPAddress = userIPAddress;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationInfo that = (TranslationInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(userIPAddress, that.userIPAddress) && Objects.equals(sourceText, that.sourceText) && Objects.equals(translatedText, that.translatedText) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userIPAddress, sourceText, translatedText, createdAt);
    }

    @Override
    public String toString() {
        return "TranslationInfo{" +
                "id=" + id +
                ", userIPAddress='" + userIPAddress + '\'' +
                ", sourceText='" + sourceText + '\'' +
                ", translatedText='" + translatedText + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
