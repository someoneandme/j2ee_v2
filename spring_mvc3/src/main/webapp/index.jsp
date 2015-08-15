<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  </head>
  
  <body>
    <h3>演示带根目录的Controller，每个方法映射一个URL</h3>
    <p>
       <a href="hello/hello_world" target="_blank">hello_world</a><br/>
       <a href="hello/easy_hello_world" target="_blank">easy_hello_world</a><br/>
       <a href="hello/hello_world?egg=egg" target="_blank">hello_world?egg=egg</a><br/>
       <a href="hello/redirect_to_helloworld" target="_blank">浏览器端跳转到hello_world</a><br/>
       <a href="hello/forward_to_helloworld" target="_blank">服务器端跳转到hello_world</a><br/>
       <br />
       <a href="validate_param?name=nicdk&email=33@fds.com" target="_blank">表單驗證例子</a>
    </p>
    
    <h3>演示中文传参并吐出json</h3>
    <p>
        <a href="chinese_json?name=测试" target="_blank">中文传参</a>
    </p>
    
    <h3>velocity演示</h3>
    <p>
       <a href="velocity_index" target="_blank">basic</a>
    </p>
    
    <h3>Cars和Form演示</h3>
    <p><a href="list_cars" target="_blank">carList</a>
       <a href="new_car" target="_blank">newCar</a>
       <a href="new_car_ognl" target="_blank">newCar_OGNL</a>
    </p>
    
    <h3>Json输出演示</h3>
    <p><a href="json" target="_blank">json</a></p>
    
    <h3>Restful演示</h3>
    <p><a href="nick/page/1" target="_blank">nick page 1</a>
       <a href="nick/page/2" target="_blank">nick page 2</a>
       <a href="nick/page/3" target="_blank">nick page 3</a> <br/>
       <a href="awoo/page/1" target="_blank">awoo page 1</a>
       <a href="awoo/page/2" target="_blank">awoo page 2</a>
       <a href="awoo/page/3" target="_blank">awoo page 3</a>
    </p>
    
    <h3>上传文件</h3>
    <p>
      <form action="upload" method="post" enctype="multipart/form-data">
        <input type="file" name="myfile" />
        <input type="submit" />
      </form>
    </p>
    
    <h3>复杂的表单（数组、form）</h3>
    <p><a href="cplx_form" target="blank">复杂的表单</a></p>
  
    <h3>velocity例子</h3>
    <p><a href="velocity_index" target="blank">velocity index</a></p>
  
    <h3>modelAttribute例子</h3>
    <p><a href="test_model_attribute?name=nick&processedName=karen&age=23" target="blank">test model attribute</a></p>
  
    <h3>异常处理例子</h3>
    <p>
       <a href="test_exception" target="blank">test_exception</a>
       <a href="test_runtime_exception" target="blank">test_runtime_exception</a>
    </p>
    
    <h3>自定义入参形式</h3>
    <p>
       <a href="test_converter?date=2015-01-18" target="blank">test_converter</a>
    </p>
    
    <h3>测试initBinder</h3>
    <p>
       <a href="test_init_binder?name=nick&age=23">age参数不会设置进去</a>
    </p>
  </body>
</html>
