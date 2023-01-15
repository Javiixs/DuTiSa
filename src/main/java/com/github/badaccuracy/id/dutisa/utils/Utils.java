package com.github.badaccuracy.id.dutisa.utils;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
public class Utils {

    @SneakyThrows
    public String hashPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Arrays.toString(hash);
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

    @SneakyThrows
    public String imageToBase64(File photo) {
        byte[] bytes = new byte[(int) photo.length()];
        return Base64.getEncoder().encodeToString(bytes);
    }

    @SneakyThrows
    public File base64ToImage(String base64) {
        byte[] bytes = Base64.getDecoder().decode(base64);
        try (OutputStream outputStream = new FileOutputStream("assets/photos/" + System.currentTimeMillis() + ".jpg")) {
            outputStream.write(bytes);
        }

        File file = new File("assets/photos/" + System.currentTimeMillis() + ".jpg");
        file.deleteOnExit();
        return file;
    }
}
