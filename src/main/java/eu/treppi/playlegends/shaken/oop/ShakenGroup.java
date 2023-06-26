package eu.treppi.playlegends.shaken.oop;

public class ShakenGroup {
    private String name, prefix;
    private int id;

    public ShakenGroup(String name, String prefix, int id) {
        this.name = name;
        this.prefix = prefix;
        this.id = id;
    }

    public ShakenGroup(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }
}
