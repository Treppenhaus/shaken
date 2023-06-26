package eu.treppi.playlegends.shaken.oop;

import java.util.UUID;

public class ShakenGroupInfo extends ShakenGroup {
    private UUID playeruuid;
    private long expires;

    public ShakenGroupInfo(String name, String prefix, int id, UUID playeruuid, long expires) {
        super(name, prefix, id);
        this.playeruuid = playeruuid;
        this.expires = expires;
    }

    public ShakenGroupInfo(ShakenGroup group, UUID playeruuid, long expires) {
        super(group.getName(), group.getPrefix(), group.getId());
        this.playeruuid = playeruuid;
        this.expires = expires;
    }

    public boolean expires() {
        return expires != -1;
    }

    public long expiresWhen() {
        return expires;
    }

    public UUID getPlayerUUID() {
        return playeruuid;
    }
}
