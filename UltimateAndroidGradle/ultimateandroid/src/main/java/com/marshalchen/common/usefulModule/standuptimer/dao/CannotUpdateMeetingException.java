/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.usefulModule.standuptimer.dao;

public class CannotUpdateMeetingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CannotUpdateMeetingException(String message) {
        super(message);
    }
}
