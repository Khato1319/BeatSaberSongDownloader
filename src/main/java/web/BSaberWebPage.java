package web;

import song.SongParameters;
import web.pageElements.Period;
import web.pageElements.SortOrder;

public class BSaberWebPage {
    private final static String format = "https://bsaber.com/songs/%s/page/%s?%s&ranked=%s&time=%s&genre=%s";
    private int page = 0;
    private SongParameters parameters;

    public BSaberWebPage(SongParameters parameters) {
        this.parameters = parameters;
    }

    public BSaberWebPage setPage(int page) {
        this.page = page;
        return this;
    }



    public String toString() {
        return format.formatted(parameters.getSortOrder(),page,parameters.getBookmark(),parameters.getRanked(),parameters.getPeriod(),parameters.getGenre());
    }




}
