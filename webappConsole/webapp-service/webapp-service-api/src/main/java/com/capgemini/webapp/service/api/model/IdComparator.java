package com.capgemini.webapp.service.api.model;

import java.util.Comparator;

public class IdComparator implements Comparator<BusinessMenuModel>{

	@Override
	public int compare(BusinessMenuModel bmm0, BusinessMenuModel bmm1) {
		if(bmm0.getMenuId() > bmm1.getMenuId()){
            return 1;
        } else {
            return -1;
        }
	}

}
