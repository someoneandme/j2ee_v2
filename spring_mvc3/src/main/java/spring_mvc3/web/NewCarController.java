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
 * 关于整个object的注入，有一块以前看过，但还没有彻底整理:@ModelAttribute
 * 好像不太好用 2015年4月27日 11:11:56 这一个在CplxFormController中有细说
 */
@Controller
public class NewCarController {

	@Autowired
	private BrandManager brandManager;

	@Autowired
	private CarManager carManager;

	@RequestMapping("list_cars")
	public String handleRequest(Model model) {
		model.addAttribute("cars", carManager.getAll());
		return "listCar";
	}
	
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
	 * 
	 * 参数可以是：@RequestParam(value = "model", required = false) String model
	 * 当传过来的参数名称和参数名称不一样时可以用上面这条
	 * 
	 * 传递过来的参数：
	 * model  price  brand
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
	 * 传过来的参数：brand.id   model   price
	 * @param model
	 * @param car
	 * @return
	 */
	@RequestMapping(value = "/new_car_ognl", method = RequestMethod.POST)
	public String postRequestOGNL(Model model, Car car) {

		carManager.add(car);

		model.addAttribute("car", car);
		return "newCarResult";
	}

}
