public class User {
    private int rank;
    private int progress;
    private static final int[] VALID_RANKS = {-8, -7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8};

    public User() {
        this.rank = -8;
        this.progress = 0;
    }

    public int getRank() {
        return rank;
    }

    public int getProgress() {
        return progress;
    }

    public void incProgress(int activityRank) {
        // Validate activity rank
        if (!isValidRank(activityRank)) {
            throw new IllegalArgumentException("The rank of an activity cannot be less than 8, 0, or greater than 8!");
        }

        // If user is already at max rank, do nothing
        if (rank == 8) {
            return;
        }

        // Calculate the difference between activity rank and user rank
        int d = calculateDifference(rank, activityRank);

        // Calculate progress to add
        int progressToAdd;
        if (d == 0) {
            progressToAdd = 3;
        } else if (d == -1) {
            progressToAdd = 1;
        } else if (d < -1) {
            progressToAdd = 0;
        } else {
            progressToAdd = 10 * d * d;
        }

        // Update progress and handle rank upgrades
        progress += progressToAdd;
        while (progress >= 100 && rank < 8) {
            progress -= 100;
            rank = getNextRank(rank);
            if (rank == 8) {
                progress = 0;
            }
        }
    }

    private boolean isValidRank(int rank) {
        for (int validRank : VALID_RANKS) {
            if (rank == validRank) {
                return true;
            }
        }
        return false;
    }

    private int calculateDifference(int userRank, int activityRank) {
        int userIndex = findRankIndex(userRank);
        int activityIndex = findRankIndex(activityRank);
        return activityIndex - userIndex;
    }

    private int findRankIndex(int rank) {
        for (int i = 0; i < VALID_RANKS.length; i++) {
            if (VALID_RANKS[i] == rank) {
                return i;
            }
        }
        return -1;
    }

    private int getNextRank(int currentRank) {
        int currentIndex = findRankIndex(currentRank);
        if (currentIndex < VALID_RANKS.length - 1) {
            return VALID_RANKS[currentIndex + 1];
        }
        return currentRank;
    }

    @Override
    public String toString() {
        return "User{" + "rank=" + rank + ", progress=" + progress + '}';
    }
}