package com.angointeam.mosaic.utils.comparator;

import com.angointeam.mosaic.domain.Script;

public class ScriptComparator implements Comparable<Script> {

    private Script mScript;

    public ScriptComparator(Script script) {
        mScript = script;
    }

    @Override
    public int compareTo(Script o) {
        if(mScript.getIdx() > o.getIdx()) return -1;
        else if (mScript.getIdx() < o.getIdx()) return 1;
        return 0;
    }
}
