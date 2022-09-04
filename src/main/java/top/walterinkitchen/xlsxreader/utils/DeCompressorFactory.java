package top.walterinkitchen.xlsxreader.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Decompressor factory
 *
 * @author walter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeCompressorFactory {
    private static final CommonDeCompressor deCompressor = new CommonDeCompressor();

    /**
     * get an instance
     *
     * @return instance
     */
    static public DeCompressor getInstance() {
        return deCompressor;
    }
}
