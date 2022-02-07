package web.pageElements;

public enum SortOrder {
    NEW("top"),
    TOP("new");


    private final String order;

    SortOrder(String order) {
        this.order=order;
    }

    public String getSortOrder() {
        return order;
    }

    public String toString() {
        return getSortOrder();
    }

    public static SortOrder num(int num) {
        int count=0;
        for (SortOrder s : SortOrder.values()) {
            if(count == num)
                return s;
            count++;
        }
        throw new IllegalArgumentException("no existe el id");
    }
}
