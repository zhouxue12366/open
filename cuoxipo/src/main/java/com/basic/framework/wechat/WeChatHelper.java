package com.basic.framework.wechat;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 解析微信.dat文件
 * @Title WeChatHelper.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年12月17日 上午10:11:54
 * @version V1.0
 */

public class WeChatHelper {
	private static LinkedBlockingQueue<File> queue = new LinkedBlockingQueue<>();
	private static final byte VALUE = (byte) 219;

	public static void main(String[] args) {

		File folder = new File("F:/2019-06");
		if (!folder.isDirectory()) {
			System.out.println("not dir");
			return;
		}
		File[] files = folder.listFiles();
		if (files == null || files.length == 0) {
			System.out.println("len = 0");
			return;
		}
		queue = new LinkedBlockingQueue<>(Arrays.asList(files));

		ExecutorService pool = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 10; i++) {
			pool.submit(new Task());
		}

		pool.shutdown();
	}

	public static class Task implements Runnable {
		private File file = queue.peek();
		private BufferedInputStream reader = null;
		private BufferedOutputStream writer = null;
		byte[] bytes = new byte[4096];

		@Override
		public void run() {
			while (file == null) {
				file = queue.poll();
			}
			do {
				try {
					reader = new BufferedInputStream(new FileInputStream(file));
					writer = new BufferedOutputStream(
							new FileOutputStream(new File("F:/accp/wechat/images/" + file.getName() + ".jpg")));

					for (int len = reader.read(bytes); len > 0; len = reader.read(bytes)) {
						for (int i = 0; i < bytes.length; i++) {
							bytes[i] = (byte) (bytes[i] ^ VALUE);
						}
						writer.write(bytes, 0, len);
						writer.flush();
					}
					System.out.println(file);
				} catch (Exception e) {
					System.out.println(e.getMessage() + "   " + file);
				} finally {
					try {
						reader.close();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					try {
						writer.close();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				file = queue.poll();
			} while (file != null || queue.size() != 0);
		}

	}
}
