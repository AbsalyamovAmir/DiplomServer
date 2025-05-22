package ru.udsu.diplomserver.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class VersionChecker {

    private static final Pattern VERSION_PATTERN = Pattern.compile(
            "(?:(?<from>(?:от\\s+)?(?<fromVersion>[\\d.]+))?" +
                    "\\s*(?:(?<to>до\\s+(?<toVersion>[\\d.]+))?)" +
                    "\\s*(?<inclusive>включительно)?)"
    );

    public boolean checkVersion(String condition, String versionToCheck) {
        if (condition == null || versionToCheck == null) {
            throw new IllegalArgumentException("Condition and version cannot be null");
        }

        if (".".equals(condition) || "-".equals(condition)) {
            return true;
        }

        String normalizedCondition = condition.trim().toLowerCase();

        if (normalizedCondition.isEmpty() ||
                normalizedCondition.matches("^[\\d.]+$")) {
            return compareVersions(versionToCheck, normalizedCondition) == 0;
        }

        Matcher matcher = VERSION_PATTERN.matcher(normalizedCondition);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid condition format: " + condition);
        }

        String fromVersion = matcher.group("fromVersion");
        String toVersion = matcher.group("toVersion");
        boolean isInclusive = matcher.group("inclusive") != null;

        boolean hasFrom = fromVersion != null;
        boolean hasTo = toVersion != null;

        if (!hasFrom && !hasTo) {
            return false;
        }

        boolean matches = true;

        if (hasFrom) {
            int fromComparison = compareVersions(versionToCheck, fromVersion);
            matches = isInclusive ? (fromComparison >= 0) : (fromComparison > 0);
        }

        if (hasTo) {
            int toComparison = compareVersions(versionToCheck, toVersion);
            matches = matches && (isInclusive ? (toComparison <= 0) : (toComparison < 0));
        }

        return matches;
    }

    private int compareVersions(String version1, String version2) {
        if (Objects.equals(version1, version2)) {
            return 0;
        }

        String[] parts1 = version1.split("\\.");
        String[] parts2 = version2.split("\\.");

        int maxLength = Math.max(parts1.length, parts2.length);

        for (int i = 0; i < maxLength; i++) {
            int num1 = i < parts1.length ? parsePart(parts1[i]) : 0;
            int num2 = i < parts2.length ? parsePart(parts2[i]) : 0;

            if (num1 != num2) {
                return num1 - num2;
            }
        }

        return 0;
    }

    private int parsePart(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}