/**
 * 创建于2011年4月15日 下午12:18:00
 */
package spring_mvc3.service;

import java.util.ArrayList;
import java.util.List;

import spring_mvc3.entity.Brand;

/**
 * 模拟数据库操作，相当于DAO层和Service层合二为一
 */
public class BrandManager {

	// 相当于数据库
	private static List<Brand> brandList = new ArrayList<Brand>();
	
	// 自增ID
	private static Long autoInc = 1L;

	/**
	 * 获得所有的数据
	 * 
	 * @return
	 */
	public List<Brand> getAll() {
		return brandList;
	}

	/**
	 * 通过品牌ID获得品牌
	 * 
	 * @param id
	 * @return
	 */
	public Brand getById(Long id) {
		if(id == null)
			return null;
		
		for (Brand brand : brandList) {
			if (brand.getId().equals(id))
				return brand;
		}
		return null;
	}

	/**
	 * 通过品牌名称获得品牌
	 * 
	 * @param name
	 * @return
	 */
	public Brand getByName(String name) {
		if(name == null)
			return null;
		
		for (Brand brand : brandList) {
			if (brand.getName().equals(name))
				return brand;
		}
		return null;
	}

	/**
	 * 插入一个新的品牌
	 * 
	 * @param brand
	 */
	public void add(Brand brand) {
		// 自增ID，不管该brand是否指定ID
		brand.setId(autoInc++);
		brandList.add(brand);
	}

	/**
	 * 通过ID删除品牌
	 * 
	 * @param id
	 */
	public void deleteById(Long id) {
		for (int i = 0; i < brandList.size(); i++) {
			if (brandList.get(i).getId() == id)
				brandList.remove(i);
		}
	}

	/**
	 * 单元测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		BrandManager brandManager = new BrandManager();
		
		Brand brand1 = new Brand();
		brand1.setId(1L);
		brand1.setName("Mercedes");
		brand1.setCountry("Germany");

		Brand brand2 = new Brand();
		brand2.setId(2L);
		brand2.setName("Peugeot");
		brand2.setCountry("France");

		brandManager.add(brand1);
		brandManager.add(brand2);

		System.out.println("size: " + brandList.size());

		Brand brand = brandManager.getById(2L);
		System.out.println("brand id: " + brand.getId() + ", name: "
				+ brand.getName());
	}
}
