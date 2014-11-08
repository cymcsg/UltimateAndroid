/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.usefulModule.standuptimer.dao;

public class InvalidTeamNameException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidTeamNameException(String message) {
        super(message);
    }
}
