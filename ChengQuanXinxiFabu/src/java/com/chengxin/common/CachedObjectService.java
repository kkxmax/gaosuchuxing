package com.chengxin.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class CachedObjectService {
    private static ConcurrentHashMap<String, CachedObject> commonCachedObjectMap = new ConcurrentHashMap<String, CachedObject>();
    private static ConcurrentHashMap<String, String> cachedPropertyMap = new ConcurrentHashMap<String, String>();
    
    private static final String C_CACHED_PROGRAM = "C_CACHED_PROGRAM";
    
    private static final String[] C_PHYSICAL_PATH_ARRAY = new String[]{
        "D:\\02_Java\\TeamMeteor\\web\\WEB-INF\\property\\@@FILE_NAME.properties"
    };
    
    public static final String C_APP_SETTINGS_NAME = "appsettings";
    
    
    public void deleteProgram() {
        commonCachedObjectMap.remove(C_CACHED_PROGRAM);
    }
    
    public void clear() {
        commonCachedObjectMap.clear();
        cachedPropertyMap.clear();
    }

    public String getProperty(String collectionName, String propertyName) throws Exception {

        String propertyValue = cachedPropertyMap.get(collectionName.toUpperCase() + "_" + propertyName.toUpperCase());

        if(propertyValue == null) {
            Properties properties = new Properties();

            String physicalPath = getClass().getResource("/").getPath();

            if (physicalPath.lastIndexOf("classes") > -1) {
                physicalPath = physicalPath.substring(0, physicalPath.lastIndexOf("classes")) + "property/" + collectionName + ".properties";
            }

            java.io.File file = new java.io.File(physicalPath);

            if (!file.exists()) {
                boolean existsFile = false;

                for (int i = 0; i < C_PHYSICAL_PATH_ARRAY.length; i++) {
                    file = new java.io.File(C_PHYSICAL_PATH_ARRAY[i].replace("@@FILE_NAME", collectionName));

                    if (file.exists()) {
                        existsFile = true;
                        break;
                    }
                }

                if (!existsFile) {
                    throw new Exception("Dont't Find!.");
                }
            }

            FileInputStream inputStream = new FileInputStream(file);
            properties.load(new BufferedInputStream(inputStream));
            inputStream.close();
            
            for(String _propertyName : properties.stringPropertyNames()) {
                cachedPropertyMap.put(collectionName.toUpperCase() + "_" + _propertyName.toUpperCase(), properties.getProperty(_propertyName, ""));
            }
            
            propertyValue = properties.getProperty(propertyName.toUpperCase(), "");
        }

        if(propertyValue == null) {
            return "";
        }
        
        return propertyValue;
    }
}
