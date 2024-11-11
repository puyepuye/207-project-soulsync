package use_case.compatibility;

import java.util.*;

public class FengshuiCalculator {

    private final Map<String, String> generationMap = new HashMap<>();
    private final Map<String, String> controlMap = new HashMap<>();
    private final Map<String, String> harmonyMap = new HashMap<>();
    private final Map<String, String> clashMap = new HashMap<>();
    private final List<String> user1stembranch = new ArrayList<>();
    private final List<String> user2stembranch = new ArrayList<>();

    public FengshuiCalculator(Date userDate1, Date userDate2) {
        // Initialize generation relationships (相生) for Heavenly Stems
        generationMap.put("JIA", "BING");   // 甲生丙
        generationMap.put("BING", "WU");    // 丙生戊
        generationMap.put("WU", "GENG");    // 戊生庚
        generationMap.put("GENG", "REN");   // 庚生壬
        generationMap.put("REN", "JIA");    // 壬生甲

        // Initialize control relationships (相剋) for Heavenly Stems
        controlMap.put("JIA", "GENG");      // 甲剋庚
        controlMap.put("BING", "REN");      // 丙剋壬
        controlMap.put("WU", "GUI");        // 戊剋癸
        controlMap.put("GENG", "JIA");      // 庚剋甲
        controlMap.put("REN", "BING");      // 壬剋丙

        // Initialize harmony relationships (六合) for Earthly Branches
        harmonyMap.put("ZI", "CHOU");       // 子丑合
        harmonyMap.put("YIN", "HAI");       // 寅亥合
        harmonyMap.put("MAO", "XU");        // 卯戌合
        harmonyMap.put("CHEN", "YOU");      // 辰酉合
        harmonyMap.put("SI", "SHEN");       // 巳申合
        harmonyMap.put("WU", "WEI");        // 午未合

        // Initialize clash relationships (相沖) for Earthly Branches
        clashMap.put("ZI", "WU");           // 子午沖
        clashMap.put("CHOU", "WEI");        // 丑未沖
        clashMap.put("YIN", "SHEN");        // 寅申沖
        clashMap.put("MAO", "YOU");         // 卯酉沖
        clashMap.put("CHEN", "XU");         // 辰戌沖
        clashMap.put("SI", "HAI");          // 巳亥沖

        user1stembranch.addAll(calculateYearStems(userDate1));
        user1stembranch.addAll(calculateMonthStems(userDate1));
        user1stembranch.addAll(calculateDayStems(userDate1));

        user2stembranch.addAll(calculateYearStems(userDate2));
        user2stembranch.addAll(calculateMonthStems(userDate2));
        user2stembranch.addAll(calculateDayStems(userDate2));
    }

    public List<String> calculateYearStems(Date date) {
        List<String> heavenlyStems = Arrays.asList("GENG", "XIN", "WU", "JI", "JIA", "YI", "BING",
                "DING", "REN", "GUI");
        List<String> earthlyBranches = Arrays.asList("ZI", "CHOU", "YIN", "MAO", "CHEN", "SI", "WU", "WEI",
                "SHEN", "YOU", "XU", "HAI");

        List<String> yearStems = new ArrayList<>();
        // Use Calendar to extract the year from the Date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        // Adjust the year relative to 1900 and calculate modulo 60
        int yearOffset = (year - 1900) % 60;

        // Calculate Heavenly Stem (天干) and Earthly Branch (地支) based on the year offset
        int heavenlyStemIndex = yearOffset % 10; // Heavenly Stem index (0 to 9)
        int earthlyBranchIndex = yearOffset % 12; // Earthly Branch index (0 to 11)

        // Output the result
        yearStems.add(heavenlyStems.get(heavenlyStemIndex));
        yearStems.add(earthlyBranches.get(earthlyBranchIndex));

        return yearStems;
    }

    public List<String> calculateMonthStems(Date date) {
        List<String> heavenlyStems = Arrays.asList("JIA", "YI", "BING", "DING", "WU", "JI", "GENG", "XIN", "REN", "GUI");
        List<String> earthlyBranches = Arrays.asList("YIN", "MAO", "CHEN", "SI", "WU", "WEI", "SHEN", "YOU", "XU",
                "HAI", "ZI", "CHOU");

        List<String> monthStems = new ArrayList<>();
        // Use Calendar to get year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;  // Calendar months are 0-based, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Adjust month value if it's January and day is 1, 2, or 0 (for some specific rule in the original Python code)
        if (month == 1 && (day == 1 || day == 2)) {
            month = Integer.parseInt(day + "" + month); // Concatenate day and month as integer if condition meets
        }

        // Calculate month stem (天干) and branch (地支)
        int yearStemIndex = (year % 10) - 3; // Simulate last digit of year in Heavenly Stem cycle, adjusted by -3
        int mon_gx = (yearStemIndex * 2 + month) % 10; // Month Heavenly Stem calculation
        int mon_zx = (month - 1) % 12;                 // Month Earthly Branch calculation, month - 1 for 0-based index

        // Adjust mon_gx if it goes out of bounds (not likely needed here but retained for logic consistency)
        if (mon_gx < 0) {
            mon_gx += 10;
        }

        // Output the result
        monthStems.add(heavenlyStems.get(mon_gx));
        monthStems.add(earthlyBranches.get(mon_zx));

        return monthStems;
    }

    public List<String> calculateDayStems(Date birthday) {
        List<String> heavenlyStems = Arrays.asList("JIA", "YI", "BING", "DING", "WU", "JI", "GENG",
                "XIN", "REN", "GUI");
        List<String> earthlyBranches = Arrays.asList("ZI", "CHOU", "YIN", "MAO", "CHEN", "SI", "WU", "WEI", "SHEN",
                "YOU", "XU", "HAI");

        List<String> dayStems = new ArrayList<>();
        // Use Calendar to get year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar months are 0-based, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Determine if it's a leap year and set February days
        int febDays;
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            febDays = 29;
        } else {
            febDays = 28;
        }

        // Calculate Day based on the F_day formula from the Python code
        int Y_2 = year % 100;
        int F_day = ((Y_2 + 7) * 5 + (Y_2 + 19) / 4 + 15) % 60;

        // Determine N_day based on month and day, adjusting for month lengths
        int N_day = 0;
        switch (month) {
            case 1:
                N_day = (F_day + day) % 60;
                break;
            case 2:
                N_day = (F_day + 31 + day) % 60;
                break;
            case 3:
                N_day = (F_day + 31 + febDays + day) % 60;
                break;
            case 4:
                N_day = (F_day + 31 + febDays + 31 + day) % 60;
                break;
            case 5:
                N_day = (F_day + 31 + febDays + 31 + 30 + day) % 60;
                break;
            case 6:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + day) % 60;
                break;
            case 7:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + day) % 60;
                break;
            case 8:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + day) % 60;
                break;
            case 9:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + 31 + day) % 60;
                break;
            case 10:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + 31 + 30 + day) % 60;
                break;
            case 11:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + day) % 60;
                break;
            case 12:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30 + day) % 60;
                break;
        }

        // Determine Heavenly Stem and Earthly Branch based on N_day
        int heavenlyStemIndex = (N_day % 10) - 1;
        int earthlyBranchIndex = (N_day % 12) - 1;

        // Ensure indices are within bounds
        if (heavenlyStemIndex < 0) heavenlyStemIndex += 10;
        if (earthlyBranchIndex < 0) earthlyBranchIndex += 12;

        dayStems.add(heavenlyStems.get(heavenlyStemIndex));
        dayStems.add(earthlyBranches.get(earthlyBranchIndex));

        return dayStems;
    }

    private int calculateStemCompatibility(String stem1, String stem2) {
        if (generationMap.get(stem1).equals(stem2)) {
            return 2; // 相生
        } else if (controlMap.get(stem1).equals(stem2)) {
            return -1; // 相剋
        }
        return 0; // 無相生或相剋
    }

    // 計算地支相合或相沖的分數
    private int calculateBranchCompatibility(String branch1, String branch2) {
        if (harmonyMap.get(branch1).equals(branch2) || harmonyMap.get(branch2).equals(branch1)) {
            return 2; // 相合
        } else if (clashMap.get(branch1).equals(branch2) || clashMap.get(branch2).equals(branch1)) {
            return -1; // 相沖
        }
        return 0; // 無相合或相沖
    }

    public int calculateCompatibilityScore(List<String> user1, List<String> user2) {
        int score = 0;

        // Year Pillar Compatibility (user1[0], user1[1] with user2[0], user2[1])
        score += calculateStemCompatibility(user1.get(0), user2.get(0)); // Year Heavenly Stem
        score += calculateBranchCompatibility(user1.get(1), user2.get(1)); // Year Earthly Branch

        // Month Pillar Compatibility (user1[2], user1[3] with user2[2], user2[3])
        score += calculateStemCompatibility(user1.get(2), user2.get(2)); // Month Heavenly Stem
        score += calculateBranchCompatibility(user1.get(3), user2.get(3)); // Month Earthly Branch

        // Day Pillar Compatibility (user1[4], user1[5] with user2[4], user2[5])
        score += calculateStemCompatibility(user1.get(4), user2.get(4)); // Day Heavenly Stem
        score += calculateBranchCompatibility(user1.get(5), user2.get(5)); // Day Earthly Branch

        // Determine compatibility based on the score
        return score;
    }

    public String getCompatibilityResult(int score) {
        if (score >= 6) {
            return "High Compatibility: A very good match!";
        } else if (score >= 3) {
            return "Moderate Compatibility: Some harmony, but also some differences.";
        } else if (score >= 0) {
            return "Low Compatibility: There are some conflicts, more effort needed.";
        } else {
            return "Very Low Compatibility: Likely a challenging relationship.";
        }
    }

    public String calculateScore() {

        int compatibilityScore = calculateCompatibilityScore(user1stembranch, user2stembranch);

        return getCompatibilityResult(compatibilityScore);
    }

}
