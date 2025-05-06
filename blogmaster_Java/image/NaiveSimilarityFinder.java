package edu.jay.fyp.featureextractor.image;

import java.awt.Color;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import edu.jay.fyp.featureextractor.FileExtractor;

/**
 * This class uses a simple similarity algorithm to compare an image
 * with all others in the same directory.
 */
public class NaiveSimilarityFinder {
    // The reference image "signature" (25 representative pixels, each in R,G,B).
    private Color[][] signature;
    private static final int BASE_SIZE = 300;
    private final String basePath;

    static {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    }

    public NaiveSimilarityFinder(String basePath) {
        this.basePath = Objects.requireNonNull(basePath, "Base path cannot be null");
    }

    /**
     * Checks if the given image is similar to any other image in the directory.
     * @param filename Path to the reference image.
     * @return true if a similar image is found, false otherwise.
     * @throws IOException if image reading fails.
     */
    public boolean isClose(String filename) throws IOException {
        Objects.requireNonNull(filename, "Filename cannot be null");
        RenderedImage ref = rescale(ImageIO.read(new File(filename)));
        signature = calcSignature(ref);

        File[] others = getOtherImageFiles();
        for (File other : others) {
            RenderedImage otherImg = rescale(ImageIO.read(other));
            double distance = calcDistance(otherImg);
            if (distance < 1500.0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Rescales an image to BASE_SIZE x BASE_SIZE pixels.
     */
    private RenderedImage rescale(RenderedImage img) {
        float scaleW = ((float) BASE_SIZE) / img.getWidth();
        float scaleH = ((float) BASE_SIZE) / img.getHeight();
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(img);
        pb.add(scaleW);
        pb.add(scaleH);
        pb.add(0.0F);
        pb.add(0.0F);
        pb.add(new InterpolationNearest());
        return JAI.create("scale", pb);
    }

    /**
     * Calculates the signature vector for the input image.
     */
    private Color[][] calcSignature(RenderedImage img) {
        Color[][] sig = new Color[5][5];
        float[] prop = { 1f / 10f, 3f / 10f, 5f / 10f, 7f / 10f, 9f / 10f };
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                sig[x][y] = averageAround(img, prop[x], prop[y]);
            }
        }
        return sig;
    }

    /**
     * Averages the pixel values around a central point and returns the average as a Color.
     */
    private Color averageAround(RenderedImage img, double px, double py) {
        RandomIter iterator = RandomIterFactory.create(img, null);
        double[] pixel = new double[3];
        double[] accum = new double[3];
        int sampleSize = 15;
        int numPixels = 0;
        int startX = (int) (px * BASE_SIZE - sampleSize);
        int endX = (int) (px * BASE_SIZE + sampleSize);
        int startY = (int) (py * BASE_SIZE - sampleSize);
        int endY = (int) (py * BASE_SIZE + sampleSize);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                iterator.getPixel(x, y, pixel);
                accum[0] += pixel[0];
                accum[1] += pixel[1];
                accum[2] += pixel[2];
                numPixels++;
            }
        }
        accum[0] /= numPixels;
        accum[1] /= numPixels;
        accum[2] /= numPixels;
        return new Color((int) accum[0], (int) accum[1], (int) accum[2]);
    }

    /**
     * Calculates the distance between the signatures of an image and the reference one.
     */
    private double calcDistance(RenderedImage other) {
        Color[][] sigOther = calcSignature(other);
        double dist = 0;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                int r1 = signature[x][y].getRed();
                int g1 = signature[x][y].getGreen();
                int b1 = signature[x][y].getBlue();
                int r2 = sigOther[x][y].getRed();
                int g2 = sigOther[x][y].getGreen();
                int b2 = sigOther[x][y].getBlue();
                double tempDist = Math.sqrt(
                        Math.pow(r1 - r2, 2) +
                        Math.pow(g1 - g2, 2) +
                        Math.pow(b1 - b2, 2)
                );
                dist += tempDist;
            }
        }
        return dist;
    }

    /**
     * Gets all image files in the same directory as the reference.
     */
    private File[] getOtherImageFiles() {
        FileExtractor fe = new FileExtractor(basePath, ".png");
        List<String> files = fe.getFiles();
        File[] others = new File[files.size()];
        for (int i = 0; i < files.size(); i++) {
            others[i] = new File(files.get(i));
        }
        return others;
    }

    // Uncomment and adapt for GUI usage if needed
    /*
    public static void main(String[] args) throws IOException {
        JFileChooser fc = new JFileChooser();
        int res = fc.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            NaiveSimilarityFinder nsm = new NaiveSimilarityFinder("D:\\fyp\\imageVideo\\so far away");
            if (nsm.isClose(file.getAbsolutePath()))
                System.out.println("done");
        } else {
            JOptionPane.showMessageDialog(null,
                    "You must select one image to be the reference.",
                    "Aborting...", JOptionPane.WARNING_MESSAGE);
        }
    }
    */
}