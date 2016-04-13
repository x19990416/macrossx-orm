/**
 * Copyright (C) 2016 X-Forever.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.macrossx.orm.ldap.GeneratedCriteria;
import com.macrossx.orm.ldap.IMapper;
import com.macrossx.orm.ldap.LdapModule;

import junit.framework.TestCase;

public class TestStart extends TestCase {
	public void testHello() {
		GeneratedCriteria c = new GeneratedCriteria();
		c.andBaseDN("cn=users");
		c.andCondition("shLibBorrower="+2145267+")");
	List<SSOUserObject> t =	Guice.createInjector(new LdapModule() {

			@Override
			public HashMap<String, String> provider() {
				HashMap<String, String> ret = Maps.newHashMap();
				ret.put("provider.url", "ldap://10.1.20.70:389/");
				ret.put("root", "DC=SHLIB");
				ret.put("security.authentication", "simple");
				ret.put("security.principal", "cn=mhlibrary");
				ret.put("security.credentials", "sysnetmhlib");
				return ret;
			}
		}).getInstance(IMapper.class).query(c, SSOUserObject.class);
	
	System.out.println(t);
	}

	
	public static class SSOUserObject {
		private String uid, cn, sn;
		private String shLibIdentityNo;
		private String userPassword;
		private String shLibUserID;
		private String shLibSid;
		private String shLibNickName;
		private String shLibBorrower;
		private String mail;
		private String mobile;
		private String shLibVPNType;
		private String shLibUseStatus;
		private String shLibFunPID;
		private String shLibCardType;
		private String shLibFunUseStatus;
		
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getShLibFunUseStatus() {
			return shLibFunUseStatus;
		}
		public void setShLibFunUseStatus(String shLibFunUseStatus) {
			this.shLibFunUseStatus = shLibFunUseStatus;
		}
		public String getShLibFunEndDate() {
			return shLibFunEndDate;
		}
		public void setShLibFunEndDate(String shLibFunEndDate) {
			this.shLibFunEndDate = shLibFunEndDate;
		}
		private String shLibFunEndDate;
		
		
		
		public String getShLibUseStatus() {
			return shLibUseStatus;
		}
		public void setShLibUseStatus(String shLibUseStatus) {
			this.shLibUseStatus = shLibUseStatus;
		}
		public String getShLibFunPID() {
			return shLibFunPID;
		}
		public void setShLibFunPID(String shLibFunPID) {
			this.shLibFunPID = shLibFunPID;
		}
		public String getShLibCardType() {
			return shLibCardType;
		}
		public void setShLibCardType(String shLibCardType) {
			this.shLibCardType = shLibCardType;
		}
		public String getMail() {
			return mail;
		}
		public String getShLibVPNType() {
			return shLibVPNType;
		}
		public void setShLibVPNType(String shLibVPNType) {
			this.shLibVPNType = shLibVPNType;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}
		public String getShLibBorrower() {
			return shLibBorrower;
		}
		public void setShLibBorrower(String shLibBorrower) {
			this.shLibBorrower = shLibBorrower;
		}
		public String getShLibNickName() {
			return shLibNickName;
		}
		public void setShLibNickName(String shLibNickName) {
			this.shLibNickName = shLibNickName;
		}
		public String getShlibaskno() {
			return shlibaskno;
		}
		public void setShlibaskno(String shlibaskno) {
			this.shlibaskno = shlibaskno;
		}
		public String getShLibUserID() {
			return shLibUserID;
		}
		public void setShLibUserID(String shLibUserID) {
			this.shLibUserID = shLibUserID;
		}
		public String getShLibSid() {
			return shLibSid;
		}
		public void setShLibSid(String shLibSid) {
			this.shLibSid = shLibSid;
		}
		private String shlibaskno;
		@Override
		public String toString() {
			return "SSOUserObject [uid=" + uid + ", cn=" + cn + ", sn=" + sn
					+ ", shLibIdentityNo=" + shLibIdentityNo + ", userPassword="
					+ userPassword + ", shLibUserID=" + shLibUserID + ", shLibSid="
					+ shLibSid + ", shLibNickName=" + shLibNickName
					+ ", shLibBorrower=" + shLibBorrower + ", mail=" + mail
					+ ", mobile=" + mobile + ", shLibVPNType=" + shLibVPNType
					+ ", shLibUseStatus=" + shLibUseStatus + ", shLibFunPID="
					+ shLibFunPID + ", shLibCardType=" + shLibCardType
					+ ", shLibFunUseStatus=" + shLibFunUseStatus
					+ ", shLibFunEndDate=" + shLibFunEndDate + ", shlibaskno="
					+ shlibaskno + "]";
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getCn() {
			return cn;
		}
		public void setCn(String cn) {
			this.cn = cn;
		}
		public String getSn() {
			return sn;
		}
		public void setSn(String sn) {
			this.sn = sn;
		}
		public String getShLibIdentityNo() {
			return shLibIdentityNo;
		}
		public void setShLibIdentityNo(String shLibIdentityNo) {
			this.shLibIdentityNo = shLibIdentityNo;
		}
		public String getUserPassword() {
			return userPassword;
		}
		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}

		


	}


}