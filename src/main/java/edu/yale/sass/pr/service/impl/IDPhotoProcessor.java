package edu.yale.sass.pr.service.impl;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;

//import com.sun.image.codec.jpeg.ImageFormatException;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Class that encapsulates ID photos. The actual bytes of the IDPhoto are
 * obtained from an <code>IPhotoBytes</code> object. This class takes those
 * bytes and creates JPEGs of various types writing them to an <CODE>OutputStream</CODE>.
 *
 * @author Peter Snow
 * @version 1.0
 * @see IPhotoBytes
 */
public class IDPhotoProcessor {
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();

	public final static int THUMBNAIL_PHOTO_WIDTH = 100;

	public final static int THUMBNAIL_PHOTO_HEIGHT = 120;

	private final static Font DEFAULT_TEXT_FONT = new Font("SansSerif", Font.BOLD, 18);

	private final static Font UNAVAILABLE_TEXT_FONT = new Font("SansSerif", Font.BOLD, 16);

	private final static Color DEFAULT_TEXT_COLOR = Color.white;

	private static Image noPhotoImage = null;

	private Canvas canvas = null;

	private MediaTracker tracker = null;

	private Font textFont = null;

	private Color textColor = null;

	private double textAngle;

	private boolean textAntiAliasing;

	private boolean textAlpha;

	private float textAlphaValue;

	private int photoWidth;

	private int photoHeight;

	private boolean photoBorder;

	private Color borderColor;

	private boolean grayscale;

	private int width;

	private int height;

	private static AtomicInteger imageId;

	public IDPhotoProcessor() {
		width = THUMBNAIL_PHOTO_WIDTH;
		height = THUMBNAIL_PHOTO_HEIGHT;
		textFont = DEFAULT_TEXT_FONT;
		textColor = DEFAULT_TEXT_COLOR;

		textAntiAliasing = false;
		textAlpha = true;
		textAlphaValue = 0.15f;
		textAngle = 0;

		photoBorder = false;
		borderColor = Color.black;
		grayscale = false;

		noPhotoImage = createNoPhotoImage(THUMBNAIL_PHOTO_WIDTH, THUMBNAIL_PHOTO_HEIGHT, "Unavailable");
		canvas = new Canvas();
		tracker = new MediaTracker(canvas);
		imageId = new AtomicInteger();
	}

	public IDPhoto getScaledWaterMarkedPhoto(byte[] bytes, String netId){
		try{
		Image photoImage = getPhotoImage(bytes);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int textX = (width / 2) - 25;
		int textY = (height / 2) +20;
		writeJPEGThumbnailWithText(out, photoImage, width, height, "YALE",
				textX, textY);
		byte[] toByteArray = out.toByteArray();
		out.close();
		return new IDPhoto(toByteArray, netId);
		}catch (Exception e) {
			IDPhoto photo = new IDPhoto(null,netId);
			photo.setCurrentAvailable(false);
			return photo;
		}
	}

	private Image getPhotoImage(byte[] photoBytes) {
		Image image = null;
		if (photoBytes != null) {
			image = toolkit.createImage(photoBytes);
			// wait for image to load
			int id = imageId.addAndGet(1);
			tracker.addImage(image, id);
			try {
				tracker.waitForID(id);
			} catch (InterruptedException ie) {
			}
			photoWidth = image.getWidth(canvas);
			photoHeight = image.getHeight(canvas);
			tracker.removeImage(image);
		} else {
			image = noPhotoImage;
		}
		return image;
	}

	private void encodeJPEG(OutputStream out, Image image, int width,
			int height, String text, int textX, int textY)
			throws  IOException {
		Graphics2D g = null;
		try {
			if (image != null) {
				BufferedImage bufImage = null;

				if (grayscale)
					bufImage = new BufferedImage(width, height,BufferedImage.TYPE_BYTE_GRAY);
				else
					bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

				g = bufImage.createGraphics();
				g.drawImage(image, 0, 0, null);

				if (text != null && !text.equals("")) {
					g.setColor(textColor);
					g.setFont(textFont);

					if (textAlpha) {
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, textAlphaValue));
					}

					if (textAngle != 0) {
						g.rotate(Math.toRadians(textAngle), textX, textY);
					}

					if (textAntiAliasing) {
						g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					}

					g.drawString(text, textX, textY);
				}

				// draw a border around the image
				if (photoBorder) {
					g.setColor(borderColor);

					g.drawLine(0, 0, photoWidth - 1, 0);
					g.drawLine(0, 0, 0, photoHeight - 1);
					g.drawLine(photoWidth - 1, 0, photoWidth - 1, photoHeight - 1);
					g.drawLine(0, photoHeight - 1, photoWidth - 1,photoHeight - 1);
				}
/*
				JPEGImageEncoder jpie = JPEGCodec.createJPEGEncoder(out);
				jpie.encode(bufImage);  */
				javax.imageio.ImageIO.write(bufImage, "jpeg", out);
			}
		} finally {
			if (g != null)
				g.dispose();
		}

	}

	private void writeJPEGThumbnailWithText(OutputStream out, Image photoImage,
			int width, int height, String text, int textX, int textY)
			throws IOException {
		if (photoImage != null) {
			Image scaledImage = photoImage.getScaledInstance(width, height,	Image.SCALE_DEFAULT);

			// wait for image to load
			int id = imageId.addAndGet(1);
			tracker.addImage(scaledImage, id);
			try {
				tracker.waitForID(id);
			} catch (InterruptedException ie) {
			}
			encodeJPEG(out, scaledImage, width, height, text, textX, textY);
			tracker.removeImage(scaledImage);
			scaledImage.flush();
			photoImage.flush();

		}
	}

	private static Image createNoPhotoImage(int width, int height,
			String noPhoto) {
		BufferedImage bufImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		WritableRaster raster = bufImage.getRaster();

		int[] pixelColors = new int[4 * width * height];

		int pixelColorsLength = pixelColors.length;

		for (int i = 0; i < pixelColorsLength; i++) {
			// light grey background
			pixelColors[i] = 200;
		}

		raster.setPixels(0, 0, width, height, pixelColors);

		Graphics g = bufImage.getGraphics();

		FontMetrics fm = g.getFontMetrics(UNAVAILABLE_TEXT_FONT);
		int noPhotoWidth = fm.stringWidth(noPhoto);

		g.setColor(DEFAULT_TEXT_COLOR);
		g.setFont(UNAVAILABLE_TEXT_FONT);
		g.drawString(noPhoto, (width / 2) - (noPhotoWidth / 2), (height / 2));
		g.dispose();

		return bufImage;
	}

	public byte[] getPublicUnavailablePhoto() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			encodeJPEG(out, noPhotoImage, width, height, "YALE", 25, 50);
			byte[] bs = out.toByteArray();
			out.close();
			return bs;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
