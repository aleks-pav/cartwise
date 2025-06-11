package lt.cartwise.user.dto;

import lt.cartwise.plan.dto.PlanWithAttributesDto;

public class UserDto {
	
	private Long id;
	private String email;
	private String name;
	private String avatarSrc;
	private PlanWithAttributesDto plan;
	
	
	public UserDto(Long id) {
		this.id = id;
	}
	
	public UserDto(Long id, String email, String name, String avatarSrc, PlanWithAttributesDto plan) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.avatarSrc = avatarSrc;
		this.plan = plan;
	}




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatarSrc() {
		return avatarSrc;
	}
	public void setAvatarSrc(String avatarSrc) {
		this.avatarSrc = avatarSrc;
	}
	public PlanWithAttributesDto getPlan() {
		return plan;
	}
	public void setPlan(PlanWithAttributesDto plan) {
		this.plan = plan;
	}
	
	
	
}
