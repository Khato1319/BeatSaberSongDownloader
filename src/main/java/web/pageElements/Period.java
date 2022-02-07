package web.pageElements;

public enum Period {
    ALL_TIME("all"),
    NINETY_DAYS("3-months"),
    THIRTY_DAYS("30-days"),
    SEVEN_DAYS("7-days"),
    ONE_DAY("24-hours");





    private String identifier;
    Period(String identifier) {
        this.identifier = identifier;
    }
    public String getIdentifier() {
        return identifier;
    }
    public String toString() {
        return getIdentifier();
    }

    public static Period num(int num) {
        int count=0;
        for (Period p : Period.values()) {
            if(count == num)
                return p;
            count++;
        }
        throw new IllegalArgumentException("no existe el id");
    }
}
