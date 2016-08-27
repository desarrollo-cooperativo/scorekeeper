package com.transition.scorekeeper.domain;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class Constants {
    public class Position {
        public static final int NONE = -1;
        public static final int FORWARD = 0;
        public static final int DEFENDER = 1;
    }

    public class PlayerConstants {
        public static final int MIN_LENGTH = 3;
        public static final int MAX_LENGTH = 10;
    }

    public class TeamConstants {
        public static final int MIN_PLAYERS = 1;
        public static final int MAX_PLAYERS = 2;
    }

    public class MatchConstants {
        public static final int MIN_TEAMS = 2;
        public static final int MAX_TEAMS = 2;
        public static final int MAX_GOALS = 7;
        public static final int MAX_MINUTES = 10;
    }
}