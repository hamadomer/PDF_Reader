package org.example.review.utils;

public final class FileUtils {
    
    private FileUtils() {}
    
    public static String getFileExtension(String fileName) {
        int idxDot = fileName.lastIndexOf('.');
        if (idxDot<0) {
            return fileName;
        }
        return fileName.substring(idxDot+1);
    }

}
