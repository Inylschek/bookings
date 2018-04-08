package com.simon.organization;

import java.util.Collections;
import java.util.Set;

public class Grunt implements IEmployee {

    private final String code;

    public Grunt(String code) {
        this.code = code;
    }

    @Override
    public String getEmployeeCode() {
        return code;
    }

    @Override
    public Set<IEmployee> getUnderlings() {
        return Collections.emptySet();
    }

    @Override
    public String toString() {
        return code;
    }

}
