/**
 * 2011年4月15日 下午12:52:05
 * 
 * 不推荐这种方式，建议使用注解那种方式
 */
package spring_mvc3.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import spring_mvc3.entity.Brand;
import spring_mvc3.entity.Car;
import spring_mvc3.service.BrandManager;
import spring_mvc3.service.CarManager;
/**
 * These methods are called before the form is displayed:

formBackingObject: initialize the Command used to init the form.
referenceData: set the view attributes (using a Map)
These are called after:

initBinder: prevent Spring to do some bindings and so them by ourselves if needed. Here we used the brand id parameter to set the actual Brand.
onSubmit: the main code. In this case, we used the command object, which is a Car, to create a new Car.
 */
/**
 * application-web.xml中配置：
 		<bean name="/new_car.html" class="springmvc.web.CarNewController">
			<property name="commandClass" value="springmvc.model.Car" />
			<property name="formView" value="carNew" />
			<property name="successView" value="list_cars.html" />
		</bean>
 */
@SuppressWarnings({ "deprecation", "unused" })
public class CarNewControllerOld extends SimpleFormController {

	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		CarManager carManager = new CarManager();
    	carManager.add((Car)command);
 
    	return new ModelAndView(new RedirectView(getSuccessView()));
	}

	//设置要传给jsp文件的数据，这里传给它品牌的列表
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
    	BrandManager brandManager = new BrandManager();
    	dataMap.put("brandList", brandManager.getAll());
    	return dataMap;
	}

	//这个类用于设置form中默认的值
//	@Override
//	protected Object formBackingObject(HttpServletRequest request)
//			throws Exception {
//		Car defaultCar = new Car();
//    	defaultCar.setModel("new model");
//    	defaultCar.setPrice(new BigDecimal(15000));
//    	return defaultCar;
//	}

	//阻止spring自动绑定brand的值，因为网页传过来的该值是int类型，所以要在这个函数中将它变成Brand类型
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.setDisallowedFields(new String[] {"brand"});
		 
    	Car car = (Car)binder.getTarget();
 
    	// set car's brand from request parameter brand id
    	BrandManager brandManager = new BrandManager();    	
    	Long brandId = null;
    	try {
	    	brandId = Long.parseLong(request.getParameter("brand"));
		} catch (Exception e) {}		
		if (brandId != null) {
			Brand brand = brandManager.getById(brandId);
			car.setBrand(brand);
		}   
	}

}
