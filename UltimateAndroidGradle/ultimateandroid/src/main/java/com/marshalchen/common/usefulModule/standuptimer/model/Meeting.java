/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.usefulModule.standuptimer.model;

import java.util.Date;
import java.util.List;

import com.marshalchen.common.usefulModule.standuptimer.dao.DAOFactory;
import com.marshalchen.common.usefulModule.standuptimer.dao.MeetingDAO;
import com.marshalchen.common.usefulModule.standuptimer.utils.Logger;
import android.content.Context;
import android.text.format.DateFormat;

public class Meeting {
    public static String DESCRIPTION_FORMAT = "MM/dd/yyyy h:mm:ssaa";

    private Long id = null;
    private Team team = null;
    private Date dateTime = null;
    private MeetingStats meetingStats = null;

    private static DAOFactory daoFactory = DAOFactory.getInstance();

    public Meeting(Team team, Date dateTime, int numParticipants, int individualStatusLength,
            int meetingLength, int quickestStatus, int longestStatus) {

        if (team == null) {
            throw new IllegalArgumentException("Meeting team must not be null");
        } else {
            this.team = new Team(team.getName());
        }

        if (dateTime == null) {
            throw new IllegalArgumentException("Meeting date/time must not be null");
        } else {
            this.dateTime = dateTime;
        }

        if (numParticipants < 1) {
            throw new IllegalArgumentException("Meeting must have at least 1 participant");
        }

        meetingStats = new MeetingStats(numParticipants, individualStatusLength, meetingLength, quickestStatus, longestStatus);
    }

    public Meeting(Long id, Team team, Date dateTime, int numParticipants, int individualStatusLength,
            int meetingLength, int quickestStatus, int longestStatus) {
        this(team, dateTime, numParticipants, individualStatusLength, meetingLength, quickestStatus, longestStatus);
        this.id = id;
    }

    public Meeting(Long id, Meeting meeting) {
        this.id = id;
        this.team = new Team(meeting.getTeam().getName());
        this.dateTime = meeting.dateTime;
        this.meetingStats = meeting.meetingStats;
    }

    public void delete(Context context) {
        MeetingDAO dao = null;
        try {
            dao = daoFactory.getMeetingDAO(context);
            dao.delete(this);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public static void deleteAllByTeam(Team team, Context context) {
        MeetingDAO dao = null;
        try {
            dao = daoFactory.getMeetingDAO(context);
            dao.deleteAllByTeam(team);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public Meeting save(Context context) {
        MeetingDAO dao = null;
        Meeting meeting = null;
        try {
            dao = daoFactory.getMeetingDAO(context);
            meeting = dao.save(this);
        } catch (Exception e) {
            Logger.e(e.getMessage());
        } finally {
            dao.close();
        }

        return meeting;
    }

    public static List<Meeting> findAllByTeam(Team team, Context context) {
        MeetingDAO dao = null;
        try {
            dao = daoFactory.getMeetingDAO(context);
            return dao.findAllByTeam(team);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public static Meeting findByTeamAndDate(Team team, Date date, Context context) {
        MeetingDAO dao = null;
        try {
            dao = daoFactory.getMeetingDAO(context);
            return dao.findByTeamAndDate(team, date);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public MeetingStats getMeetingStats() {
        return meetingStats;
    }

    public String getDescription() {
        return DateFormat.format(DESCRIPTION_FORMAT, dateTime).toString();
    }
}
