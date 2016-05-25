/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.usefulModule.standuptimer.dao;

public class DuplicateTeamException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicateTeamException(String message) {
        super(message);
    }
}
