package com.tps.tpi.utils;

import java.io.InputStream;
import java.io.IOException;

/**
 * User: Bjorn Harvold
 * Date: Oct 8, 2008
 * Time: 3:41:22 PM
 * Description:
 */
public interface ImageResizer {
    InputStream resize(InputStream image, int size, String extension) throws IOException;
}
