package com.coupllector.svc.domain.common.region;

import java.util.HashMap;
import java.util.Map;

public enum AustraliaState {
    VIC("VICTORIA", "Victoria", "VIC"),
    NSW("NEW_SOUTH_WALES", "New South Wales", "NSW"),
    ACT("AUSTRALIAN_CAPITAL_TERRITORY", "Australian Capital Territory", "ACT"),
    QLD("QUEENSLAND", "Queensland", "QLD"),
    NT("NORTHERN_TERRITORY", "Northern Territory", "NT"),
    TAS("TASMANIA", "Tasmania", "TAS"),
    WA("WESTERN_AUSTRALIA", "Western Australia", "WA"),
    SA("SOUTH_AUSTRALIA", "South Australia",  "SA");

    /**
     * State's system name
     */
    private String systemName;

    /**
     * The state's name
     */
    private String name;

    /**
     * The state's abbreviation
     */
    private String abbreviation;

    /**
     * The set of states addressed by abbreviations.
     */
    private static final Map<String, AustraliaState> AUSTRALIA_STATE_BY_ABBR = new HashMap<>();
    private static final Map<String, AustraliaState> AUSTRALIA_STATE_BY_SYSTEM_NAME = new HashMap<>();

    /**
     * Static initializer
     */
    static {
        for (AustraliaState state : values()) {
            AUSTRALIA_STATE_BY_ABBR.put(state.getAbbreviation(), state);
            AUSTRALIA_STATE_BY_SYSTEM_NAME.put(state.getSystemName(), state);
        }
    }

    /**
     * Construct a new Australian state
     * @param name
     * @param abbreviation
     */
    AustraliaState(String systemName, String name, String abbreviation) {
        this.systemName = systemName;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    /**
     * Return the state's abbreviation
     * @return
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the enum constant with the specified abbreviation.
     *
     * @param abbreviation the state's abbreviation.
     * @return the enum constant with the specified abbreviation.
     */
    public static AustraliaState valueOfAbbreviation(final String abbreviation) {
        final AustraliaState state = AUSTRALIA_STATE_BY_ABBR.get(abbreviation);

        if (state != null) {
            return state;
        } else {
            throw new IllegalArgumentException(String.format("Invalid state abbreviation: [%s].", abbreviation));
        }
    }

    public static AustraliaState valueOfName(final String name) {
        final String enumSystemName = name.toUpperCase().replaceAll(" ", "_");

        final AustraliaState state = AUSTRALIA_STATE_BY_SYSTEM_NAME.get(enumSystemName);

        if (state != null) {
            return state;
        } else {
            throw new IllegalArgumentException(String.format("Invalid state abbreviation: [%s].", name));
        }
    }

    @Override
    public String toString(){
        return name;
    }
}
