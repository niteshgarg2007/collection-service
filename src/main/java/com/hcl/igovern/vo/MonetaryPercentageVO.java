package com.hcl.igovern.vo;

import org.springframework.stereotype.Component;

@Component
public class MonetaryPercentageVO {
	
	private Long clmSumId;
	private Double monetVaPct;
	private Double monetOutOfSttPct;
	private Double monetUcfePct;
	private Double monetUcxPct;
	
	public Long getClmSumId() {
		return clmSumId;
	}
	public void setClmSumId(Long clmSumId) {
		this.clmSumId = clmSumId;
	}
	public Double getMonetVaPct() {
		return monetVaPct;
	}
	public void setMonetVaPct(Double monetVaPct) {
		this.monetVaPct = monetVaPct;
	}
	public Double getMonetOutOfSttPct() {
		return monetOutOfSttPct;
	}
	public void setMonetOutOfSttPct(Double monetOutOfSttPct) {
		this.monetOutOfSttPct = monetOutOfSttPct;
	}
	public Double getMonetUcfePct() {
		return monetUcfePct;
	}
	public void setMonetUcfePct(Double monetUcfePct) {
		this.monetUcfePct = monetUcfePct;
	}
	public Double getMonetUcxPct() {
		return monetUcxPct;
	}
	public void setMonetUcxPct(Double monetUcxPct) {
		this.monetUcxPct = monetUcxPct;
	}
}
