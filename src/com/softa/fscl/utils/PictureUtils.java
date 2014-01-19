package com.softa.fscl.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

public class PictureUtils {
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}

	}

	/**
	 * 根据指定的图像路径和最小高/宽度来获取缩略图
	 * 
	 * @param imagePath
	 *            图像的路径
	 * @param minLen
	 *            指定输出图像的最小高/宽度
	 * @return 生成的缩略图
	 */
	public static Bitmap getImageThumbnail(String imagePath, int minLen) {
		int width = 300;
		int height = 300;
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false;

		if (options.outHeight > options.outWidth) {
			width = minLen;
			options.inSampleSize = options.outWidth / width;
			height = options.outHeight * width / options.outWidth;
			options.outHeight = height;
			options.outWidth = width;
		} else {
			height = minLen;
			options.inSampleSize = options.outHeight / height;
			width = options.outWidth * height / options.outHeight;
			options.outWidth = width;
			options.outHeight = height;
		}

		options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		options.inPurgeable = true;
		options.inInputShareable = true;

		bitmap = BitmapFactory.decodeFile(imagePath, options);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 根据指定的图像路径和最小高/宽度来获取缩略图
	 * 
	 * @param imagePath
	 *            图像的路径
	 * @param minLen
	 *            指定输出图像的最小高/宽度
	 * @return 生成的缩略图
	 */
	public static Bitmap getImageThumbnail(byte[] data, int minLen) {
		int width = 300;
		int height = 300;
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inJustDecodeBounds = false;

		if (options.outHeight > options.outWidth) {
			width = minLen<=0?width:minLen;
			options.inSampleSize = options.outWidth / width;
			height = options.outHeight * width / options.outWidth;
			options.outHeight = height;
			options.outWidth = width;
		} else {
			height = minLen<=0?height:minLen;
			options.inSampleSize = options.outHeight / height;
			width = options.outWidth * height / options.outHeight;
			options.outWidth = width;
			options.outHeight = height;
		}

		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inPurgeable = true;
		options.inInputShareable = true;

		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
}
