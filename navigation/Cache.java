package navigation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import static navigation.Parameters.*;

public class Cache {
	private static File file;
	private static BufferedImage image;
	private static Parameters params;
	
	public Cache(String path, Parameters parameters) {
		Cache.params = parameters;
		Cache.file = new File(path);
		Cache.image = this.load();
	}
	
    private BufferedImage load() {
        if (Cache.file.exists()) {
            try {
                return ImageIO.read(Cache.file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new BufferedImage(params.SCREEN_WIDTH, params.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }
    
    public void save(BufferedImage image) {
    	try {
            ImageIO.write(image, "png", Cache.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public BufferedImage getImage() {
    	return Cache.image;
    }
}
