package song;

import web.BSaberWebPage;
import web.pageElements.Period;
import web.pageElements.SortOrder;

public class SongParameters {
    public Period period;
    private String username;
    private boolean ranked;
    private String genre;
    private SortOrder sortOrder;

    private SongParameters() {

    }

    public String getBookmark() {
        return username != null ? "bookmarked_by=" + username : "";
    }

    public String setting() {
        return String.format("%s%s songs from period = %s and genre = %s with ranked = %s",
                sortOrder, username == null ? "" : " bookmarked from " + username,period,genre,ranked);
    }

    public String getGenre() {
        return genre;
    }

    public String getSortOrder() {
        return sortOrder.toString();
    }

    public String getPeriod() {
        return period.toString();
    }

    public String getRanked() {
        return ranked ? "true" : "false";
    }

    public static class Builder {

        public Period period = Period.ALL_TIME;
        private String username;
        private boolean ranked = false;
        private String genre = "all";
        private SortOrder sortOrder = SortOrder.TOP;

        public SongParameters.Builder setBookmark(String username) {
            if (!username.equals(""))
                this.username = username;
            return this;
        }

        public SongParameters.Builder setRanked(boolean ranked) {
            this.ranked = ranked;
            return this;
        }

        public SongParameters.Builder setSortOrder(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public SongParameters.Builder setGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public SongParameters.Builder setPeriod(Period period) {
            this.period = period;
            return this;
        }

        public SongParameters build() {
            SongParameters parameters = new SongParameters();
            parameters.period = period;
            parameters.genre = genre;
            parameters.sortOrder = sortOrder;
            parameters.ranked = ranked;
            parameters.username = username;
            return parameters;
        }
    }
}
