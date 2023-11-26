package com.shasha.blog.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Integer userId;

	@NotBlank(message = "Username is required Character should be  4 to 20 in between")
	@Size(max = 20, min = 4)
	private String name;

	@NotBlank(message = "Email is required Character should be  4 to 50 in between")
	@Size(min = 4, max = 50)
	private String email;

	@NotBlank(message = "Password is required Character should be  6 to 20 in between")
	@Size(min = 6, max = 20)
	private String pwd;
	@NotBlank(message = "about is required Character should be  4 to 2000 in between")
	@Size(min = 4, max = 2000)
	private String about;

}
