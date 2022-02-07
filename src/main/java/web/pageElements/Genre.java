package web.pageElements;

public enum Genre {
    ALL("all"),
    KPOP("k-pop"),
    POP("pop"),
    SPEED("speed"),
    FITNESS("fitness");
    private final String identifier;
    Genre(String genre) {
        this.identifier = genre;
    }
    public String getGenre() {
        return identifier;
    }
    public String toString() {
        return getGenre();
    }
    public static Genre num(int num) {
        int count=0;
        for (Genre g : Genre.values()) {
            if(count == num)
                return g;
            count++;
        }
        throw new IllegalArgumentException("no existe el id");
    }

}
