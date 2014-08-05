package spring_mvc3.web.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class ValidateStudent {

	@NotEmpty(message = "姓名不能为空")
	@Length(min = 2, max = 10, message = "姓名长度必须在2到10之间")
	private String name;

	@NotEmpty(message = "邮箱不能为空")
	@Email(message = "邮箱格式不正确")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
