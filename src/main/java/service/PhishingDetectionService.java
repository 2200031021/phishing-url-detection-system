package service;

public class PhishingDetectionService {

    // MAIN METHOD USED BY SERVLET
    public int calculateRiskScore(String url, String fullText) {
        int score = 0;

        if (url == null || url.isEmpty()) {
            return 0;
        }

        if (url.length() > 75) score += 20;
        if (url.contains("@")) score += 25;
        if (url.contains("-")) score += 10;
        if (url.matches(".*\\d+\\.\\d+\\.\\d+\\.\\d+.*")) score += 30;
        if (!url.startsWith("https")) score += 15;

        if (containsSocialEngineering(fullText)) score += 15;

        return Math.min(score, 100);
    }

    // ✅ DOMAIN REPUTATION HEURISTIC (no external APIs)
    public int applyDomainReputation(String url, int previousCount) {
        int reputationScore = 0;

        if (previousCount >= 3) {
            reputationScore += 20; // Frequently checked domain
        }

        if (!url.startsWith("https")) {
            reputationScore += 10; // Insecure protocol
        }

        if (url.length() < 20 || url.contains("-")) {
            reputationScore += 10; // Suspicious domain pattern
        }

        return reputationScore;
    }

    // ✅ NEW: Detect social-engineering language in messages
    public int detectSocialEngineering(String message) {
        if (message == null) return 0;

        String text = message.toLowerCase();
        int score = 0;

        if (text.contains("urgent")) score += 10;
        if (text.contains("verify")) score += 10;
        if (text.contains("account blocked")) score += 15;
        if (text.contains("click immediately")) score += 10;
        if (text.contains("login now")) score += 10;
        if (text.contains("suspended")) score += 10;

        return score;
    }

    public String getResult(int score) {
        if (score >= 60) return "PHISHING";
        if (score >= 30) return "SUSPICIOUS";
        return "SAFE";
    }

    // EXISTING SOCIAL ENGINEERING FLAG (boolean)
    private boolean containsSocialEngineering(String text) {
        if (text == null) return false;

        String msg = text.toLowerCase();
        return msg.contains("urgent") ||
               msg.contains("verify") ||
               msg.contains("account") ||
               msg.contains("click") ||
               msg.contains("blocked") ||
               msg.contains("login");
    }
}
