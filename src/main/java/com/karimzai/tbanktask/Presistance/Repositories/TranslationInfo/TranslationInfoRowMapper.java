package com.karimzai.tbanktask.Presistance.Repositories.TranslationInfo;

import com.karimzai.tbanktask.Domain.Entities.TranslationInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class TranslationInfoRowMapper implements RowMapper<TranslationInfo> {
    @Override
    public TranslationInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        TranslationInfo translationInfo = new TranslationInfo();
        translationInfo.setId(UUID.fromString(rs.getString("id")));
        translationInfo.setSourceText(rs.getString("source_text"));
        translationInfo.setTranslatedText(rs.getString("translated_text"));
        translationInfo.setUserIPAddress(rs.getString("user_ip"));
        translationInfo.setCreatedAt(rs.getTimestamp("created_at"));
        return translationInfo;
    }
}
