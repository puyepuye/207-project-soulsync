package data_access.Cloudinary;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryUploader {
//    public static final String CLOUD_NAME = "dvf7ebgzz";
//    private static final String CLOUDINARY_API_KEY = "API_KEY";
//    private static final String CLOUDINARY_API_SECRET = "API_SECRET";

    private Cloudinary cloudinary;

    public CloudinaryUploader() {
//        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", "CLOUD_NAME",
//                "cloudinary_api_key", "CLOUDINARY_API_KEY",
//                "cloudinary_api_secret", "CLOUDINARY_API_SECRET"));
        // Load environment variables
        Dotenv dotenv = Dotenv.load();

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", dotenv.get("CLOUD_NAME"),
                "api_key", dotenv.get("CLOUDINARY_API_KEY"),
                "api_secret", dotenv.get("CLOUDINARY_API_SECRET")
        ));
    }

//     Initialize Cloudinary
//    public static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//            "cloud_name", CLOUD_NAME,
//            "cloudinary_api_key", CLOUDINARY_API_KEY,
//            "cloudinary_api_secret", CLOUDINARY_API_SECRET));

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
