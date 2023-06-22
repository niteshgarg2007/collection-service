package com.hcl.igovern.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="V_ITS_BAD_ACTOR_ADDRESS")
public class VITSBadActorAddressEO implements Serializable {
	
	private static final long serialVersionUID = -5605275940940586050L;
	
	@Id
	@Column(name="BAD_ACTOR_ID")
	private Long badActorId;
	
	@Column(name="BAD_ACTOR_NAME")
	private String badActorName;
	
	@Column(name="ADDRESS_LINE1")
	private String addressLine1;
	
	@Column(name="ADDRESS_LINE2")
	private String addressLine2;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="STT")
	private String stt;
	
	@Column(name="ZIP_CD")
	private String zipCd;

}
