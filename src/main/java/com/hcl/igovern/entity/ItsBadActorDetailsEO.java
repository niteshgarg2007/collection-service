package com.hcl.igovern.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hcl.igovern.auditing.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ITS_BAD_ACTOR_DETAILS")
public class ItsBadActorDetailsEO extends Auditable<String> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BAD_ACTOR_ID")
	private Long badActorId;	
	
	@Column(name = "BAD_ACTOR_SSN")
	private String badActSSN;
	
	@Column(name = "DUMMY_IND")
	private String dummyInd;
	
	@Column(name = "FIRST_NM")
	private String firstName;
	
	@Column(name = "LAST_NM")
	private String lastName;
	
	@Column(name = "DOB")
	private Timestamp dateOfBirth;
	
	@Column(name = "PHONE_NUM")
	private String phoneNumber;
	
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name = "PHY_CNTRY_CD")
	private String phyCountry;
	
	@Column(name = "PHY_ADDRESS_LINE1")
	private String phyAddLine1;
	
	@Column(name = "PHY_ADDRESS_LINE2")
	private String phyAddLine2;
	
	@Column(name = "PHY_CITY")
	private String phyCity;
	
	@Column(name = "PHY_STT_CD")
	private String phyState;
		
	@Column(name = "PHY_ZIP_CD")
	private String phyZip;
	
	@Column(name = "PHY_ZIP_PLUS_FOUR")
	private String phyZipPlus;
	
	@Column(name = "PHY_CNTY_ID")
	private Long phyCounty;
	
	@Column(name = "MAIL_CNTRY_CD")
	private String mailCountry;
	
	@Column(name = "MAIL_ADDRESS_LINE1")
	private String mailAddLine1;
	
	@Column(name = "MAIL_ADDRESS_LINE2")
	private String mailAddLine2;
	
	@Column(name = "MAIL_CITY")
	private String mailcity;
	
	@Column(name = "MAIL_STT_CD")
	private String mailState;
	
	@Column(name = "MAIL_ZIP_CD")
	private String mailZip;
	
	@Column(name = "MAIL_ZIP_PLUS_FOUR")
	private String mailZipPlus;
	
	@Column(name = "MAIL_CNTY_ID")
	private Long mailCounty;
	
}
