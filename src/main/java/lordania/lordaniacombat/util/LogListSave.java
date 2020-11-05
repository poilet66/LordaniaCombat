package lordania.lordaniacombat.util;

import org.apache.commons.lang.ArrayUtils;

public class LogListSave {

    String[] uuids;

    public LogListSave(String[] list) {
        this.uuids = list;
    }
    public LogListSave() {

    }

    public void add(String uuid) {
        this.uuids = (String[]) ArrayUtils.add(this.uuids, uuid);
    }

    public void remove(String uuid) {
        int index = ArrayUtils.indexOf(this.uuids, uuid, 0);
        this.uuids = (String[]) ArrayUtils.remove(this.uuids, index);
    }

    public boolean isLogged(String uuid) {
        return ArrayUtils.contains(this.uuids, uuid);
    }

}
