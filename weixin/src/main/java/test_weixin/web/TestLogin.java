package test_weixin.web;

import com.alibaba.fastjson.JSON;

import weixin.popular.api.TokenAPI;
import weixin.popular.bean.token.Token;

public class TestLogin {

	public static void main(String[] args) {
		String appid = "wxd2aa42cb5fda82be";
		String secret = "a513ae836267e90e11f05b1ef9fcff65";
		
		Token token = TokenAPI.token(appid, secret);
		
		System.out.println(JSON.toJSONString(token, true));
	}

}
