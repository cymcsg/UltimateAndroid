/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.usefulModule.standuptimer.model;

import java.util.ArrayList;
import java.util.List;

import com.marshalchen.common.usefulModule.standuptimer.dao.DAOFactory;
import com.marshalchen.common.usefulModule.standuptimer.dao.TeamDAO;
import com.marshalchen.common.usefulModule.standuptimer.utils.Logger;
import android.content.Context;

public class Team {
    private Long id = null;
    private String name = null;
    private static DAOFactory daoFactory = DAOFactory.getInstance();

    public Team(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Team name must not be null");
        }
        this.name = name.trim();
    }

    public Team(Long id, String name) {
        this(name);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void delete(Context context) {
        TeamDAO dao = null;
        try {
            Meeting.deleteAllByTeam(this, context);
            dao = daoFactory.getTeamDAO(context);
            dao.delete(this);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public static Team create(String name, Context context) {
        TeamDAO dao = null;
        Team team = null;
        try {
            dao = daoFactory.getTeamDAO(context);
            team = dao.save(new Team(name));
        } catch (Exception e) {
            Logger.e(e.getMessage());
        } finally {
            dao.close();
        }

        return team;
    }

    public int getNumberOfMeetings(Context context) {
        return findAllMeetings(context).size();
    }

    public MeetingStats getAverageMeetingStats(Context context) {
        List<MeetingStats> meetingStats = new ArrayList<MeetingStats>();
        List<Meeting> meetings = findAllMeetings(context);
        for (Meeting meeting : meetings) {
            meetingStats.add(meeting.getMeetingStats());
        }
        return MeetingStats.getAverageStats(meetingStats);
    }

    public List<Meeting> findAllMeetings(Context context) {
        return Meeting.findAllByTeam(this, context);
    }

    public boolean hasMeetings(Context context) {
        return findAllMeetings(context).size() > 0;
    }

    public static Team findByName(String teamName, Context context) {
        TeamDAO dao = null;
        try {
            dao = daoFactory.getTeamDAO(context);
            return dao.findByName(teamName);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public static List<String> findAllTeamNames(Context context) {
        TeamDAO dao = null;
        try {
            dao = daoFactory.getTeamDAO(context);
            return dao.findAllTeamNames();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
}
