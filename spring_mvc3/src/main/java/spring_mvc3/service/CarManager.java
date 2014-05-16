/**
 * 2011年4月15日 下午04:40:12
 */
package spring_mvc3.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import spring_mvc3.entity.Brand;
import spring_mvc3.entity.Car;

/**
 * 模拟数据库操作，相当于DAO层和Service层合二为一
 */
public class CarManager {

	// 依赖注入
	private BrandManager brandManager;

	// 相当于数据库
	private static List<Car> carList = new ArrayList<Car>();

	// 自增ID
	private static Long autoInc = 1L;

	// 初始化数据
	public CarManager() {
		
		// 【注意】BrandManager必须在这里先实例好
		// 实际上，这些初始化数据的代码是不会出现在这里的，在实际应用中
		brandManager = new BrandManager();

		Brand brand1 = new Brand();
		brand1.setName("Mercedes");
		brand1.setCountry("Germany");

		Brand brand2 = new Brand();
		brand2.setId((long) 2);
		brand2.setName("Peugeot");
		brand2.setCountry("France");

		Car car1 = new Car();
		car1.setId((long) 1);
		car1.setBrand(brand1);
		car1.setModel("SL 500");
		car1.setPrice(new BigDecimal(40000));

		Car car2 = new Car();
		car2.setId((long) 2);
		car2.setBrand(brand2);
		car2.setModel("607");
		car2.setPrice(new BigDecimal(35000));

		brandManager.add(brand1);
		brandManager.add(brand2);

		this.add(car1);
		this.add(car2);
	}

	/**
	 * 获得汽车列表
	 */
	public List<Car> getAll() {
		return carList;
	}

	/**
	 * 通过汽车ID获得Car
	 * 
	 * @param id
	 * @return
	 */
	public Car getById(Long id) {
		for (Car car : carList) {
			if (car.getId() == id)
				return car;
		}
		return null;
	}

	/**
	 * 通过汽车的牌子获得该牌子的所有汽车
	 * 
	 * @param brank
	 * @return
	 */
	public List<Car> getByBrankName(String brankName) {
		List<Car> result = new ArrayList<Car>();
		Brand brand = brandManager.getByName(brankName);
		if (brand != null) {
			for (Car car : carList) {
				if (car.getBrand() != null
						&& car.getBrand().getName().equals(brand.getName()))
					result.add(car);
			}
		}
		return result;
	}

	/**
	 * 添加汽车
	 */
	public void add(Car car) {
		car.setId(autoInc++);

		Brand brand = car.getBrand();
		if (brand == null) {
			car.setBrand(null);
		} else {
			Brand brand2 = brandManager.getById(brand.getId());
			if (brand2 == null) {
				// 级联新增品牌
				brandManager.add(brand);
				car.setBrand(brand);
			} else {
				car.setBrand(brand2);
			}
		}

		car.setModel(car.getModel());
		car.setPrice(car.getPrice());

		carList.add(car);
	}
	
	/**
	 * 通过汽车ID删除汽车
	 * @param id
	 */
	public void deleteById(Long id){
		for (int i = 0; i < carList.size(); i++) {
			if (carList.get(i).getId() == id)
				carList.remove(i);
		}
	}

	public BrandManager getBrandManager() {
		return brandManager;
	}

	public void setBrandManager(BrandManager brandManager) {
		this.brandManager = brandManager;
	}

}
