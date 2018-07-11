package com.capgemini.webapp.ldap.repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import com.capgemini.webapp.dao.api.entity.LdapUser;
import com.capgemini.webapp.dao.api.entity.UserProfile;
import com.capgemini.webapp.security.config.LdapUserModel;

@Service
@Configurable
public class UserRepository implements BaseLdapNameAware {

	@Autowired
	private LdapTemplate ldapTemplate;

	private LdapName baseLdapPath;

	public void setBaseLdapPath(LdapName baseLdapPath) {
		this.baseLdapPath = baseLdapPath;
	}

	public void create(LdapUser p) {
		
		 Name dn = buildDn(p); 
		 ldapTemplate.bind(dn, null, buildAttributes(p));
		 

		/*DirContext dctx = null;
		final Hashtable<Object, Object> env = new Hashtable<Object, Object>();
		try {

			String url = "ldap://localhost:389";
			String conntype = "simple";
			String AdminDn = "cn=Manager,dc=maxcrc,dc=com";
			String password = "secret";

			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, url);
			env.put(Context.SECURITY_AUTHENTICATION, conntype);
			env.put(Context.SECURITY_PRINCIPAL, AdminDn);
			env.put(Context.SECURITY_CREDENTIALS, password);
			dctx = new InitialDirContext(env);
			// Create a container set of attributes
			final Attributes container = new BasicAttributes();

			// Create the objectclass to add
			final Attribute objClasses = new BasicAttribute("objectClass");
			objClasses.add("inetOrgPerson");

			// Assign the username, first name, and last name
			final Attribute commonName = new BasicAttribute("cn", p.getFullName());
			final Attribute email = new BasicAttribute("mail", p.getEmail());
			final Attribute givenName = new BasicAttribute("givenName", p.getFullName());
			final Attribute uid = new BasicAttribute("uid", p.getUid());
			final Attribute surName = new BasicAttribute("sn", p.getLastName());

			// Add password
			final Attribute userPassword = new BasicAttribute("userpassword", p.getPassword());

			DirContextAdapter context = new DirContextAdapter(buildDn(p));
			for (UserProfile userProfile : p.getUserProfile()) {

				Name roleDn = buildGroupDn(userProfile.getType());
				DirContextOperations ctx = ldapTemplate.lookupContext(roleDn);
				//ctx.addAttributeValue("uniqueMember", "uid=" + p.getUid() + ", ou=People, dc=maxcrc,dc=com");
				context.setAttributeValues("objectclass", new String[] {"top", "inetOrgPerson"});
				context.setAttributeValue("cn", p.getFullName());
				context.setAttributeValue("mail", p.getEmail());
				context.setAttributeValue("givenName", p.getFullName());
				context.setAttributeValue("uid", p.getUid());
				context.setAttributeValue("sn", p.getLastName());
				context.setAttributeValue("userpassword", p.getPassword());
				ctx.addAttributeValue("uniqueMember",context);
				try {
					//ldapTemplate.modifyAttributes(ctx);
					ldapTemplate.bind(ctx);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}

			// Add these to the container
			container.put(objClasses);
			container.put(commonName);
			container.put(givenName);
			container.put(email);
			container.put(uid);
			container.put(surName);
			container.put(userPassword);
			
			 ModificationItem[] mods = new ModificationItem[1];

	            Attribute mod =
	                new BasicAttribute("uniqueMember",getUserDN(p.getFullName()));
	            mods[0] =
	                new ModificationItem(DirContext.ADD_ATTRIBUTE, mod);
	           
	            
			// Create the entry
			for (UserProfile userProfile : p.getUserProfile()) {
				
				//dctx.createSubcontext(getUserDN(userProfile.getType()), container);
				 dctx.modifyAttributes(getGroupDN("Manager"), mods);
			}
		} */
		/*catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != dctx) {
				try {
					dctx.close();
				} catch (final NamingException e) {
					System.out.println("Error in closing ldap " + e);
				}
			}
		}*/
		/*
		 * DirContextAdapter context = new DirContextAdapter(dn);
		 * context.setAttributeValues("objectclass", new String[] {"top",
		 * "inetOrgPerson"}); context.setAttributeValue("cn", p.getFullName());
		 * context.setAttributeValue("sn", p.getLastName());
		 * context.setAttributeValue("description","Test"); ldapTemplate.bind(context);
		 */
	}

	private String getGroupDN(String type) {
		return new StringBuffer()
                .append("cn=")
                .append(type)
                .append(",")
                .append("ou=People")
                .toString();
	}

	private Name buildGroupDn(String groupName) {
		return LdapNameBuilder.newInstance("ou=People").add("cn", groupName).build();
	}

	private String getUserDN(String userName) {
		String userDN = new StringBuffer().append("uid=").append(userName).append(",OU=People,dc=maxcrc,dc=com")
				.toString();
		//System.out.println(userDN);
		return userDN;
	}

	public List<LdapUserModel> findAll() {
		EqualsFilter filter = new EqualsFilter("objectclass", "inetOrgPerson");
		return ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), new PersonContextMapper());
	}

	public LdapUserModel findOne(String uid) {
		Name dn = LdapNameBuilder.newInstance().add("ou", "People").add("objectclass", "inetOrgPerson").add("uid", uid).build();
		return ldapTemplate.lookup(dn, new PersonContextMapper());
	}

	public List<LdapUserModel> findByName(String name) {
		LdapQuery q = query().where("objectclass").is("person").and("cn").whitespaceWildcardsLike(name);
		return ldapTemplate.search(q, new PersonContextMapper());
	}

	public void update(LdapUser p) {
		ldapTemplate.rebind(buildDn(p), null, buildAttributes(p));
	}

	public void updateLastName(LdapUser p) {
		Attribute attr = new BasicAttribute("sn", p.getLastName());
		ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
		ldapTemplate.modifyAttributes(buildDn(p), new ModificationItem[] { item });
	}

	public void delete(LdapUser p) {
		ldapTemplate.unbind(buildDn(p));
	}

	private Name buildDn(LdapUser p) {
		//baseLdapPath = LdapUtils.newLdapName("dc=maxcrc,dc=com");
		return LdapNameBuilder.newInstance().add("ou", "People").add("uid", p.getUid()).build();
	}

	private Attributes buildAttributes(LdapUser p) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("top");
		ocAttr.add("inetOrgPerson");
		ocAttr.add("posixAccount");
		attrs.put(ocAttr);
		attrs.put("ou", "People");
		attrs.put("uid", p.getUid());
		attrs.put("cn", p.getFirstName());
		attrs.put("sn", p.getLastName());
		attrs.put("mail", p.getEmail());
		attrs.put("userPassword", "admin");
		attrs.put("uidNumber", "32578");
		attrs.put("gidNumber", "0");
		attrs.put("homeDirectory", "LBS");
		return attrs;
	}

	private static class PersonContextMapper extends AbstractContextMapper<LdapUserModel> {
		public LdapUserModel doMapFromContext(DirContextOperations context) {
			LdapUserModel person = new LdapUserModel();
			person.setFirstName(context.getStringAttribute("cn"));
			person.setLastName(context.getStringAttribute("sn"));
			person.setUid(context.getStringAttribute("uid"));
			person.setEmail(context.getStringAttribute("mail"));
			// System.out.println(" RoleNames :"+context.getStringAttributes("memberof"));
			return person;
		}
	}	
}
