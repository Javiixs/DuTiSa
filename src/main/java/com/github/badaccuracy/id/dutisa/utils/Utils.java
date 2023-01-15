package com.github.badaccuracy.id.dutisa.utils;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
public class Utils {

    @SneakyThrows
    public String hashPassword(String password) {
        // sha-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            final String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    @SneakyThrows
    public File getFile(String path) {
        File dutisaJar = new File(DuTiSa.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        if (dutisaJar.isFile()) {
            try (Closer closer = new Closer()) {
                JarFile jarFile = closer.add(new JarFile(dutisaJar));
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().startsWith(path)) {
                        return new File(dutisaJar.getParentFile(), entry.getName());
                    }
                }
            }
        } else {
            File dir = getResourceFile("");
            assert dir != null;
            for (File file : Objects.requireNonNull(dir.listFiles(f -> f.getName().startsWith(path)))) {
                return file;
            }
        }

        return null;
    }

    @Nullable
    private static File getResourceFile(String path) {
        File file = null;
        try {
            file = new File(Utils.getResource(path).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private URL getResource(String path) {
        return DuTiSa.class.getClassLoader().getResource(path);
    }

}
