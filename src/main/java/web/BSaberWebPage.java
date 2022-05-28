package web;

import song.SongParameters;

public class BSaberWebPage {
    private final static String format = "https://bsaber.com/songs/%s/page/%s?%s&ranked=%s&time=%s&genre=%s";
    private int page = 0;
    private final SongParameters parameters;

    public BSaberWebPage(SongParameters parameters) {
        this.parameters = parameters;
    }

    public BSaberWebPage setPage(int page) {
        this.page = page;
        return this;
    }

    public String toString() {
        return String.format(format,parameters.getSortOrder(),page,parameters.getBookmark(),parameters.getRanked(),parameters.getPeriod(),parameters.getGenre());
    }




}
