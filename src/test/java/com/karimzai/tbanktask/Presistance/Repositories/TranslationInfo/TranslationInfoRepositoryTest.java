package com.karimzai.tbanktask.Presistance.Repositories.TranslationInfo;

import com.karimzai.tbanktask.Presistance.Repositories.AbstractRepositoryUnitTest;
import com.karimzai.tbanktask.Domain.Entities.TranslationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TranslationInfoRepositoryTest extends AbstractRepositoryUnitTest {
    private TranslationInfoRepository underTest;

    @BeforeEach
    void setup() {
        underTest = new TranslationInfoRepository(
                new JdbcTemplate(getDataSource()),
                new TranslationInfoRowMapper()
        );
    }

    @Test
    void saveTranslationInfo() {
        // given
        TranslationInfo translationInfo = new TranslationInfo();
        translationInfo.setSourceText("test text");
        translationInfo.setTranslatedText("translated text");
        translationInfo.setUserIPAddress("ip address");

        // when
        underTest.saveTranslationInfo(translationInfo);

        // then
        assertThat(translationInfo.getId()).isNotNull();
        assertThat(translationInfo.getSourceText()).isEqualTo("test text");
        assertThat(translationInfo.getTranslatedText()).isEqualTo("translated text");
        assertThat(translationInfo.getUserIPAddress()).isEqualTo("ip address");
        assertThat(translationInfo.getCreatedAt()).isNotNull();
        assertThat(translationInfo.getCreatedAt()).isBefore(Instant.now());
    }

    @Test
    void getTranslationInfo() {
        // given
        TranslationInfo translationInfo = new TranslationInfo();
        translationInfo.setSourceText("test text");
        translationInfo.setTranslatedText("translated text");
        translationInfo.setUserIPAddress("ip address");

        // when
        underTest.saveTranslationInfo(translationInfo);
        TranslationInfo translationInfoFromDb = underTest.getTranslationInfo(translationInfo.getId()).get();

        // then
        assertThat(translationInfoFromDb).isNotNull();
        assertThat(translationInfoFromDb.getId()).isEqualTo(translationInfo.getId());
        assertThat(translationInfoFromDb.getSourceText()).isEqualTo("test text");
        assertThat(translationInfoFromDb.getTranslatedText()).isEqualTo("translated text");
        assertThat(translationInfoFromDb.getUserIPAddress()).isEqualTo("ip address");
        assertThat(translationInfoFromDb.getCreatedAt()).isEqualTo(translationInfo.getCreatedAt());
        assertThat(translationInfoFromDb.getCreatedAt()).isBefore(Instant.now());
    }

    @Test
    void getTranslationHistoryList() {
        TranslationInfo translationInfo = new TranslationInfo();
        translationInfo.setSourceText("test text");
        translationInfo.setTranslatedText("translated text");
        translationInfo.setUserIPAddress("ip address");

        // when
        underTest.saveTranslationInfo(translationInfo);
        List<TranslationInfo> translationHistoryList = underTest.getTranslationHistoryList(1, 10, false);

        // then
        assertThat(translationHistoryList).isNotNull();
        assertThat(translationHistoryList).isNotEmpty();
        boolean existInserted = translationHistoryList.stream()
                .anyMatch(tInfo -> tInfo.getId().equals(translationInfo.getId()));
        assertThat(existInserted).isTrue();
    }
}