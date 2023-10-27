package com.github.ulrikewerner.backend.entityTests;

import com.github.ulrikewerner.backend.entities.NflLogoAcronym;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NflLogoAcronymTest {
    @Test
    void NflLogoAcronym_expectThatTheValuesOfTheEnumAreNotNull_ForTheFirst20() {
        assertNotNull(NflLogoAcronym.valueOf("ARI"));
        assertNotNull(NflLogoAcronym.valueOf("ATL"));
        assertNotNull(NflLogoAcronym.valueOf("BAL"));
        assertNotNull(NflLogoAcronym.valueOf("BUF"));
        assertNotNull(NflLogoAcronym.valueOf("CAR"));
        assertNotNull(NflLogoAcronym.valueOf("CHI"));
        assertNotNull(NflLogoAcronym.valueOf("CIN"));
        assertNotNull(NflLogoAcronym.valueOf("CLE"));
        assertNotNull(NflLogoAcronym.valueOf("DAL"));
        assertNotNull(NflLogoAcronym.valueOf("DEN"));
        assertNotNull(NflLogoAcronym.valueOf("DET"));
        assertNotNull(NflLogoAcronym.valueOf("GB"));
        assertNotNull(NflLogoAcronym.valueOf("HOU"));
        assertNotNull(NflLogoAcronym.valueOf("IND"));
        assertNotNull(NflLogoAcronym.valueOf("JAX"));
        assertNotNull(NflLogoAcronym.valueOf("KC"));
        assertNotNull(NflLogoAcronym.valueOf("LAC"));
        assertNotNull(NflLogoAcronym.valueOf("LAR"));
        assertNotNull(NflLogoAcronym.valueOf("LV"));

    }

    @Test
    void NflLogoAcronym_expectThatTheValuesOfTheEnumAreNotNull_ForTheLastOne() {
        assertNotNull(NflLogoAcronym.valueOf("MIA"));
        assertNotNull(NflLogoAcronym.valueOf("MIN"));
        assertNotNull(NflLogoAcronym.valueOf("NE"));
        assertNotNull(NflLogoAcronym.valueOf("NFL"));
        assertNotNull(NflLogoAcronym.valueOf("NO"));
        assertNotNull(NflLogoAcronym.valueOf("NYG"));
        assertNotNull(NflLogoAcronym.valueOf("NYJ"));
        assertNotNull(NflLogoAcronym.valueOf("PHI"));
        assertNotNull(NflLogoAcronym.valueOf("PIT"));
        assertNotNull(NflLogoAcronym.valueOf("SEA"));
        assertNotNull(NflLogoAcronym.valueOf("SF"));
        assertNotNull(NflLogoAcronym.valueOf("TB"));
        assertNotNull(NflLogoAcronym.valueOf("TEN"));
        assertNotNull(NflLogoAcronym.valueOf("WAS"));
    }
}
