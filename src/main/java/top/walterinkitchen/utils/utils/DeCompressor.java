package top.walterinkitchen.utils.utils;

import java.io.File;

/**
 * Decompressor
 *
 * @author walter
 * @since 1.0
 */
public interface DeCompressor {
    /**
     * decompress zip
     *
     * @param src src file
     * @return Decompressed folder
     */
    File decompressZip(File src);
}
