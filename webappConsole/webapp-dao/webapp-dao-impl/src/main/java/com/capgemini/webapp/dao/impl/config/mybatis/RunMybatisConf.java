package com.capgemini.webapp.dao.impl.config.mybatis;

import java.util.Iterator;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.dao.api.entity.UserProfile;
import com.capgemini.webapp.dao.impl.config.hibernate.HibernateConfig;
import com.capgemini.webapp.dao.impl.config.mybatis.mapper.usermapper.UserMapper;

public class RunMybatisConf {
	static {
        System.setProperty("config", "dev");
    }
	public static void main(String[] args) {
	   AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	   ctx.register(HibernateConfig.class,MyBatisConfig.class);
	   ctx.refresh();
	   UserMapper mapper = ctx.getBean(UserMapper.class);	
	   User u= mapper.retrieveUserBySSOID("awarhoka");
	   u.setUserProfiles(mapper.getUserProfile(u.getId()));
	   System.err.println("User :"+u);
	   Set<UserProfile>  profiles = u.getUserProfiles();
	   Iterator<UserProfile> itr= profiles.iterator();
	   while (itr.hasNext()) {
		UserProfile prof = (UserProfile) itr.next();
		  System.err.println("User Profile :"+prof.getId() +" "+ prof.getType());
	}
	   ctx.close();
	}
} 
