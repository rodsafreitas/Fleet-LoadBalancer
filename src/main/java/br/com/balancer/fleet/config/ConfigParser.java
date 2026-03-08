package br.com.balancer.fleet.config;

import br.com.balancer.fleet.model.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigParser {

    private static final Pattern SERVER_PATTERN = Pattern
            .compile("(http://localhost:\\d+)(?:\\s+weight=(\\d+))?");

    private static final int DEFAULT_WEIGHT = 1;

    public static List<Address> parseServers(String config) {
        if (config == null || config.trim().isEmpty()) {
            throw new IllegalArgumentException("Configuration cannot be null or empty");
        }

        Matcher matcher = SERVER_PATTERN.matcher(config);
        List<Address> servers = new ArrayList<>();

        while (matcher.find()) {
            String url = matcher.group(1);
            String weightGroup = matcher.group(2);

            int weight = parseWeight(weightGroup);
            servers.add(new Address(url, weight));
        }

        if (servers.isEmpty()) {
            throw new IllegalArgumentException("No valid server configurations found");
        }

        return servers;
    }

    /**
     * Parses weight value from string, defaulting to 1 if null or invalid.
     */
    private static int parseWeight(String weightString) {
        if (weightString == null) {
            return DEFAULT_WEIGHT;
        }

        try {
            int weight = Integer.parseInt(weightString);
            return weight > 0 ? weight : DEFAULT_WEIGHT;
        } catch (NumberFormatException e) {
            return DEFAULT_WEIGHT;
        }
    }
}