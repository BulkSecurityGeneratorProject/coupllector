package com.coupllector.svc.domain.common.region;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Australia state enum
 */
public class AustraliaStateUnitTest {

    @Test
    public void should_get_enum_NSW_when_NSW_abbreviation_is_passed() {
        AustraliaState actualState = AustraliaState.valueOfAbbreviation("NSW");

        assertThat(actualState).isEqualTo(AustraliaState.NSW);
    }

    @Test
    public void should_get_enum_NSW_when_NSW_name_is_passed() {
        AustraliaState actualState = AustraliaState.valueOfName("New South Wales");

        assertThat(actualState).isEqualTo(AustraliaState.NSW);
    }

}
