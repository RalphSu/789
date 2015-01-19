package com.icebreak.p2p.base;

import com.icebreak.util.env.Env;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpSession;

public class Image {
	public static Color getRandColor(int fc, int bc) {
		// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	// 生成随机字符
	private static String getRandChar(int length) {
		String code = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
//			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
			String charOrNum = "char";
			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				code += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				code += String.valueOf(random.nextInt(10));
			}
		}
		return code;
	}
	
	public static  BufferedImage creatImage(HttpSession session) {
		//初始化验证码
		String sRand = "";
		// 在内存中创建图象
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 115; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 取随机产生的认证码(4位数字)
		for (int i = 0; i < 4; i++) {
			String rand = getRandChar(1);
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            if(!Env.isOnline()){
                g.drawString("8", 13 * i + 6, 16);
            }else{
                g.drawString(rand, 13 * i + 6, 16);
            }

		}
		// 图象生效
		g.dispose();
        if(!Env.isOnline()){
            sRand = "8888";
        }
		session.setAttribute("imgCode", sRand);
		return image;
	}
	
	public static boolean checkImgCode(HttpSession session, String imgCode) {
		boolean bool = false;
		String oldImgCode = (String) session.getAttribute("imgCode");
		if (imgCode.equalsIgnoreCase(oldImgCode)) {
			bool = true;
		}
		return bool;
	}
}
