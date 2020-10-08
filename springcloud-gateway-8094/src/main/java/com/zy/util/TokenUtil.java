package com.zy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zy.entity.Userinfo;

import javax.sound.midi.Soundbank;
import java.util.Date;
import java.util.HashMap;

public class TokenUtil {
	 private static final long EXPIRE_TIME= 20*24*60*60*1000;//20天
	 private static final String TOKEN_SECRET="tokenjoinlabs321";  //密钥盐

	 /**
	 * 签名生成  生成加密字符串
	 * @param user
	 * @return
			 */
	public static String sign(Userinfo user){

		String token = null;
		try {
			Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
			token = JWT.create()
					.withIssuer("zy")
					.withClaim("userName", user.getUserName())
					.withClaim("userId", ""+user.getUserId())
					.withExpiresAt(expiresAt)
					// 使用了HMAC256加密算法。
					.sign(Algorithm.HMAC256(TOKEN_SECRET));
		} catch (Exception e){
			e.printStackTrace();
		}
		return token;

	}

	/**
	 * 签名验证
	 * @param token
	 * @return
	 */
	public static HashMap<String,String> verify(String token){
		HashMap<String,String> map = new HashMap<String,String>();

		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("zy").build();
			DecodedJWT jwt = verifier.verify(token);
			System.out.println("认证通过：");
			System.out.println("issuer: " + jwt.getIssuer());
			System.out.println("userId: " + jwt.getClaim("userId").asString());
			System.out.println("username: " + jwt.getClaim("userName").asString());
			System.out.println("过期时间：      " + jwt.getExpiresAt());
			map.put("userId",jwt.getClaim("userId").asString());
			map.put("userName",jwt.getClaim("userName").asString());
			return map;
		} catch (Exception e){
			System.out.println("认证失败：");
			return null;
		}

	}

	public static void main(String[] args) {
		Userinfo userinfo = new Userinfo().setUserName("小明").setUserPwd("123");
		String token = TokenUtil.sign(userinfo);
		System.out.println(TokenUtil.verify(token));
	}

}
