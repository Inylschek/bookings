package com.simon.organization;

import java.util.Set;

public interface IEmployee {
	public String getEmployeeCode();
	
	public Set<IEmployee> getUnderlings();
}
