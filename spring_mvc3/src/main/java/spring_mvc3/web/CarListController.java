/**
 * 2011年4月15日 下午06:17:31
 */
package spring_mvc3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import spring_mvc3.service.CarManager;

/**
 * 该类继续演示【注入】
 * 如果这里没有RequestMapping，则默认为根目录
 * 【注意】service层的类不要放在方法参数中，因为参数的对象都是临时的，来一个创建一个
 */
@Controller
public class CarListController {

	/**
	 * 自动注入，推荐
	 */
	@Autowired(required = true)
	private CarManager carManager;

	/**
	 * 【注意】carManager不要写在参数里
	 */
	@RequestMapping("list_cars")
	public ModelAndView handleRequest() {
		ModelAndView modelAndView = new ModelAndView("listCar");
		modelAndView.addObject("cars", carManager.getAll());
		return modelAndView;
	}

	public CarManager getCarManager() {
		return carManager;
	}

	public void setCarManager(CarManager carManager) {
		this.carManager = carManager;
	}

}
