/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.uimodule.standuptimer.model;

import java.util.List;

public class MeetingStats {
    private float numParticipants = 0;
    private float individualStatusLength = 0;
    private float meetingLength = 0;
    private float quickestStatus = 0;
    private float longestStatus = 0;

    public MeetingStats(float numParticipants, float individualStatusLength, float meetingLength, float quickestStatus, float longestStatus) {
        this.numParticipants = numParticipants;
        this.individualStatusLength = individualStatusLength;
        this.meetingLength = meetingLength;
        this.quickestStatus = quickestStatus;
        this.longestStatus = longestStatus;
    }

    public static MeetingStats getAverageStats(List<MeetingStats> meetingStatsList) {
        float totalNumParticipants = 0;
        float totalIndividualStatusLength = 0;
        float totalMeetingLength = 0;
        float totalQuickestStatus = 0;
        float totalLongestStatus = 0;

        for (MeetingStats meetingStats : meetingStatsList) {
            totalNumParticipants += meetingStats.getNumParticipants();
            totalIndividualStatusLength += meetingStats.getIndividualStatusLength();
            totalMeetingLength += meetingStats.getMeetingLength();
            totalQuickestStatus += meetingStats.getQuickestStatus();
            totalLongestStatus += meetingStats.getLongestStatus();
        }

        return new MeetingStats(totalNumParticipants / meetingStatsList.size(),
                totalIndividualStatusLength / meetingStatsList.size(),
                totalMeetingLength / meetingStatsList.size(),
                totalQuickestStatus / meetingStatsList.size(),
                totalLongestStatus / meetingStatsList.size());
    }

    public float getNumParticipants() {
        return numParticipants;
    }

    public float getIndividualStatusLength() {
        return individualStatusLength;
    }

    public float getMeetingLength() {
        return meetingLength;
    }

    public float getQuickestStatus() {
        return quickestStatus;
    }

    public float getLongestStatus() {
        return longestStatus;
    }
}
