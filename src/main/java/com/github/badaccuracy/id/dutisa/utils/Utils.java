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

    public enum PasswordStrength {
		INVALID,
		WEAK,
		MEDIUM,
		STRONG
	}
	
	public static boolean validateType(String input) {
		Pattern pattern = Pattern.compile(
				"[a-z|A-Z|0-9|~|`|!| |@|"
				+ "#|$|%|^|&|\\*|(|)|_|-|+|"
				+ "=|{|}|[|]|||\\|:|;|\"|"
				+ "'|<|,|>|.|?]*"
		);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	public static PasswordStrength validate(String input) {
		if (!validateType(input)) {
			return PasswordStrength.INVALID;
		}
		
		final int length = input.length();

		int symbolMatches = 0;
		int capitalLetterMatches = 0;
		int numberMatches = 0;
		
		// Patterns
		Pattern symbolsPattern = Pattern.compile(
				"[~|`|!| |@|#|$|%|^|&|\\\\*|(|)|_|-|+|=|{|}|[|]|||\\\\|:|;|\\\"|'|<|,|>|.|?]"
		);
		Pattern capitalLetterPattern = Pattern.compile("[A-Z]");
		Pattern numberPattern = Pattern.compile("[0-9]");
		
		// Matchers 
		Matcher symbolMatcher = symbolsPattern.matcher(input);
		Matcher capitalLetterMatcher = capitalLetterPattern.matcher(input);
		Matcher numberMatcher = numberPattern.matcher(input);
		
		// Calculate Matches for Symbols, Capital Letters, and Numbers
		while (symbolMatcher.find()) {
			symbolMatches++;
		}
		
		while (capitalLetterMatcher.find()) {
			capitalLetterMatches++;
		}
		
		while (numberMatcher.find()) {
			numberMatches++;
		}
		
		if (length > 8 && validateType(input)) {
			/* Strong Condition
			 * Has more than 12 characters
			 * Contains capital letters
			 * Contains numbers
			 * Contains symbols
			 * */
			if (length >= 12 && 
				capitalLetterMatches >= 1 && 
				numberMatches >= 1 && 
				symbolMatches >= 1
			) {
				return PasswordStrength.STRONG;
			}
			/* Medium Condition
			 * Has more than 8 but less than 12 characters
			 * Contains capital letters
			 * Contains numbers
			 * */
			else if (capitalLetterMatches >= 1 && 
					 numberMatches >= 1
			) {
				return PasswordStrength.MEDIUM;
			}
			/* Weak Condition
			 * Only contains letters
			 * */
			else {
				return PasswordStrength.WEAK;
			}
		}
		
		return PasswordStrength.INVALID;
	}
    
}
