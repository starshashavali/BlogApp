package com.shasha.blog.domain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "User_Dtls")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity  implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String name;

	private String email;

	private String pwd;

	private String about;
	@OneToMany(mappedBy="userEntity",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<PostEntity> posts;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="user_role",
	joinColumns=@JoinColumn(name="user",
	referencedColumnName="userId"),
	inverseJoinColumns=@JoinColumn(name="role",
	referencedColumnName="roleId"))
	private List<RoleEntity> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return   this.roles.stream().
				map(role->new SimpleGrantedAuthority(role.getRoleName()))
				.collect(Collectors.toList());
	}
	

	@Override
	public String getPassword() {
		return this.pwd;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
}
