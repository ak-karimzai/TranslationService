package com.karimzai.tbanktask.Presistance.Repositories.TranslationInfo;

import com.karimzai.tbanktask.Application.Contracts.Presistence.ITranslationInfoRepository;
import com.karimzai.tbanktask.Domain.Entities.TranslationInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TranslationInfoRepository implements ITranslationInfoRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TranslationInfoRowMapper translationInfoRowMapper;

    public TranslationInfoRepository(JdbcTemplate jdbcTemplate, TranslationInfoRowMapper translationInfoRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.translationInfoRowMapper = translationInfoRowMapper;
    }

    @Override
    public void saveTranslationInfo(TranslationInfo translationInfo) {
        String query = "INSERT INTO translation_info (source_text, translated_text, user_ip) " +
                        "VALUES (?, ?, ?) " +
                        "RETURNING id, created_at";

        jdbcTemplate.execute(query, (PreparedStatementCallback<TranslationInfo>) ps -> {
            ps.setString(1, translationInfo.getSourceText());
            ps.setString(2, translationInfo.getTranslatedText());
            ps.setString(3, translationInfo.getUserIPAddress());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                translationInfo.setCreatedAt(rs.getTimestamp("created_at"));
                translationInfo.setId(UUID.fromString(rs.getString("id")));
                return translationInfo;
            } else {
                throw new SQLException("An error occurred while saving translation info");
            }
        });
    }

    @Override
    public Optional<TranslationInfo> getTranslationInfo(UUID id) {
        String query = "SELECT id, source_text, translated_text, user_ip, created_at " +
                "FROM translation_info WHERE id = ?";
        return jdbcTemplate.query(query, translationInfoRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public List<TranslationInfo> getTranslationHistoryList(int pageNumber, int pageSize, boolean orderByCreation) {
        String query = "SELECT id, source_text, translated_text, user_ip, created_at "
                + "FROM translation_info ";
        if (orderByCreation) {
            query += " ORDER BY created_at DESC ";
        }
        query += " LIMIT ? OFFSET ?";
        int offset = (pageNumber - 1) * pageSize;
        return jdbcTemplate.query(query,
                translationInfoRowMapper, pageSize, offset);
    }
}
