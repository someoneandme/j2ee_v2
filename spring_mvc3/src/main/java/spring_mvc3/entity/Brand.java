/**
 * 2011年4月15日 下午04:54:34
 */
package spring_mvc3.entity;

/**
 * 汽车的牌子
 * @author Pugwoo
 *
 */
public class Brand {

	private Long id;
	
	private String name;
	
	private String country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString(){
		return getName();
	}
}
