package top.walterinkitchen.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * ResourceFileUtils
 *
 * @author walter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceFileUtils {
    /**
     * return resource file by path
     *
     * @param path path
     * @return file
     */
    public static File getResources(String path) {
        URL resource = ResourceFileUtils.class.getClassLoader().getResource(path);
        try {
            assert resource != null;
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
