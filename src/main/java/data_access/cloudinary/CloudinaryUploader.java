package data_access.cloudinary;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryUploader {

    private Cloudinary cloudinary;

    public CloudinaryUploader() {
        // Load environment variables
        Dotenv dotenv = Dotenv.load();

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", dotenv.get("CLOUD_NAME"),
                "api_key", dotenv.get("CLOUDINARY_API_KEY"),
                "api_secret", dotenv.get("CLOUDINARY_API_SECRET")
        ));
    }

    // Uploads image to Cloudinary & returns public URL of uploaded image.
    public String uploadImage(File imageFile) {
        try {
            Map uploadImg = cloudinary.uploader().upload(imageFile, ObjectUtils.emptyMap());
            String imageURL = (String) uploadImg.get("url");
            return imageURL;
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("Image upload failed" + e.getMessage());
            return null;
        }
    }
}
