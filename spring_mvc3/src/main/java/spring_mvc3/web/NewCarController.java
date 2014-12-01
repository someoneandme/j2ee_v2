/**
 * 2011年4月15日 下午06:18:04
 */
package spring_mvc3.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring_mvc3.entity.Car;
import spring_mvc3.service.BrandManager;
import spring_mvc3.service.CarManager;

/**
 * 涉及的内容：
 * 根据Request的GET和POST来选择方法
 * 
 * 【重要问题】从Form传过来的参数在执行完subContronller后会被Spring再次放入request中，如果重名，则会被覆盖
 */
@Controller
public class NewCarController {

	@Autowired(required = true)
	private BrandManager brandManager;

	@Autowired(required = true)
	private CarManager carManager;

	/**
	 * GET调用
	 */
	@RequestMapping(value = "/new_car", method = RequestMethod.GET)
	public String getRequest(Model model) {
		model.addAttribute("brands", brandManager.getAll());
		return "newCar";
	}

	/**
	 * POST调用
	 * 参数可以是：@RequestParam(value = "model", required = false) String model
	 */
	@RequestMapping(value = "/new_car", method = RequestMethod.POST)
	public String postRequest(Model modelView, // 由于名字冲突，所以换了个名字
			String model, BigDecimal price,
			/*@RequestParam("brand") Long brandId*/ Long brand) {
		
		Long brandId = brand;
		
		// for log
		System.out.println("postRequest model:" + model + ",price:" + price
				+ ",brandId:" + brandId);

		Car car = new Car();
		car.setModel(model);
		car.setPrice(price);
		car.setBrand(brandManager.getById(brandId));

		carManager.add(car);

		modelView.addAttribute("car", car);
		return "newCarResult";
	}

	/**
	 * GET调用
	 */
	@RequestMapping(value = "/new_car_ognl", method = RequestMethod.GET)
	public String getRequestOGNL(Model model) {
		model.addAttribute("brands", brandManager.getAll());
		return "newCarOGNL";
	}

	/**
	 * POST调用
	 * 参数可以是：@RequestParam(value = "model", required = false) String model
	 * 当传过来的参数名称和参数名称不一样时可以用上面这条
	 * 
	 * 【同名问题】当方法执行完之后，Spring又把参数car放入到request中，导致car被覆盖，使用ModelAndView可以解决
	 */
	@RequestMapping(value = "/new_car_ognl", method = RequestMethod.POST)
	public String postRequestOGNL(Model model, Car car) {

		carManager.add(car);

		model.addAttribute("car", car);
		return "newCarResult";
	}

	public BrandManager getBrandManager() {
		return brandManager;
	}

	public void setBrandManager(BrandManager brandManager) {
		this.brandManager = brandManager;
	}

	public CarManager getCarManager() {
		return carManager;
	}

	public void setCarManager(CarManager carManager) {
		this.carManager = carManager;
	}

}
