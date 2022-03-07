package bot.translation;

import java.io.*;
import java.util.ArrayList;

public class TranslateWhitelist {

    private static final String PATH = "./data/whitelist.txt";

    private static final ArrayList<String> WHITELIST = new ArrayList<>(10);

    public static void load() throws IOException {
        synchronized (WHITELIST) {
            WHITELIST.clear();
            BufferedReader br = new BufferedReader(new FileReader(PATH));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty())
                    continue;
                WHITELIST.add(line);
            }
            br.close();
        }
    }

    public static void add(String name) {
        synchronized (WHITELIST) {
            if (WHITELIST.contains(name))
                return;
            WHITELIST.add(name);
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(PATH));
                for (String s : WHITELIST) {
                    if (s == null)
                        continue;
                    bw.write(s);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bw != null) try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void remove(String name) {
        synchronized (WHITELIST) {
            if (!WHITELIST.contains(name))
                return;
            WHITELIST.remove(name);
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(PATH));
                for (String s : WHITELIST) {
                    if (s == null)
                        continue;
                    bw.write(s);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bw != null) try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isWhiteListed(String name) {
        synchronized (WHITELIST) {
            return WHITELIST.contains(name);
        }
    }
}
