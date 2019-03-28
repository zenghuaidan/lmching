package com.lmching.mall.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lmching")
@EnableConfigurationProperties
//@PropertySource("classpath:/country.yml")
public class MallProperties {
	
//	private List<Country> countrys;
	
	private String domain;
	
	private String merchantId;
	
	private String paypalUrl;
	
	private String paypalIpnUrl;
	
	private String facebookPixelCode;
	
	private boolean enableFacebookPixelCode;
	
	private String googleTagCode;
	
	private boolean enableGoogleTag;
	
	private String fromMail;

//	public List<Country> getCountrys() {
//		return countrys;
//	}
//
//	public void setCountrys(List<Country> countrys) {
//		this.countrys = countrys;
//	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPaypalUrl() {
		return paypalUrl;
	}

	public void setPaypalUrl(String paypalUrl) {
		this.paypalUrl = paypalUrl;
	}

	public String getPaypalIpnUrl() {
		return paypalIpnUrl;
	}

	public void setPaypalIpnUrl(String paypalIpnUrl) {
		this.paypalIpnUrl = paypalIpnUrl;
	}

	public String getFacebookPixelCode() {
		return facebookPixelCode;
	}

	public void setFacebookPixelCode(String facebookPixelCode) {
		this.facebookPixelCode = facebookPixelCode;
	}

	public boolean isEnableFacebookPixelCode() {
		return enableFacebookPixelCode;
	}

	public void setEnableFacebookPixelCode(boolean enableFacebookPixelCode) {
		this.enableFacebookPixelCode = enableFacebookPixelCode;
	}

	public String getGoogleTagCode() {
		return googleTagCode;
	}

	public void setGoogleTagCode(String googleTagCode) {
		this.googleTagCode = googleTagCode;
	}

	public boolean isEnableGoogleTag() {
		return enableGoogleTag;
	}

	public void setEnableGoogleTag(boolean enableGoogleTag) {
		this.enableGoogleTag = enableGoogleTag;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

//	public List<Pair<String, String>> getCountrys(Locale locale) {
//		List<Pair<String, String>> list = new ArrayList<>();
//		for(Country country : countrys) {
//			list.add(Pair.of(country.getId(), country.getName(locale)));
//		}
//		return list;
//	}

	public static class Country {
		private String id;
		private String nameEn;
		private String nameTc;
		private String nameSc;
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getNameEn() {
			return nameEn;
		}

		public void setNameEn(String nameEn) {
			this.nameEn = nameEn;
		}

		public String getNameTc() {
			return nameTc;
		}

		public void setNameTc(String nameTc) {
			this.nameTc = nameTc;
		}

		public String getNameSc() {
			return nameSc;
		}

		public void setNameSc(String nameSc) {
			this.nameSc = nameSc;
		}

		public String getName(Locale locale) {
			return locale.equals(Locale.CHINA) ? this.nameSc : (locale.equals(Locale.TAIWAN) ? this.nameTc : this.nameEn);	
		}
	}
}

