package com.riseworld.reddit.android.ui.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper for the logging of the APP
 **/
public class RedditRiseFileHelper {

    private static final String TAG = "RedditRiseFileHelper";

    public static void checkRedditDir() {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + PropertiesReader.getProperty("dirReddit"));
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {
            if (Constants.DEBUG) {
                Log.e(TAG, PropertiesReader.getProperty("errorCreatingDir") + " " + e.getMessage());
            }
        }
    }

    /**
     * @param sText
     */
    public static void LOG(String sText) {
        Date now = null;
        SimpleDateFormat formatter = null;
        File dir = null;
        File redditFile = null;
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            String sLOG = PropertiesReader.getProperty("LOG");
            if (Constants.OFF_.equals(sLOG)) {
                return;
            }
            formatter = new SimpleDateFormat(PropertiesReader.getProperty("formatLogFile"));
            now = new Date();
            String dirReddit
                    = PropertiesReader.getProperty("dirReddit");
            dir = new File(Environment.getExternalStorageDirectory() + dirReddit);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName =
                    PropertiesReader.getProperty("fileName") + "_" + formatter.format(now) + ".txt";
            redditFile =
                    new File(Environment.getExternalStorageDirectory() +
                            PropertiesReader.getProperty("dirReddit"), fileName);
            formatter = new SimpleDateFormat(PropertiesReader.getProperty("formatLogDetail"));
            String lineBeginning = formatter.format(now);
            writer = new FileWriter(redditFile, true);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(lineBeginning + ":" + sText);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
            writer.close();

        } catch (Exception e) {
            if (Constants.DEBUG) {
                Log.e(TAG, PropertiesReader.getProperty("errorCreatingDir") + " " + e.getMessage());
            }
        } finally {
            if (now != null) {
                now = null;
            }
            if (formatter != null) {
                formatter = null;
            }
            if (dir != null) {
                dir = null;
            }
            if (redditFile != null) {
                redditFile = null;
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                    bufferedWriter = null;
                } catch (Exception ignored) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                    writer = null;
                } catch (Exception ignored) {
                }
            }
        }
    }


    /**
     * @param file
     * @return
     */
    public static String readFile(File file) {
        String content = null;
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (Exception e) {
            if (Constants.DEBUG) {
                e.printStackTrace();
                Log.e(TAG, PropertiesReader.getProperty("errorReading") + " " + e.getMessage());
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ignored) {
                }
            }
        }
        return content;
    }

    /**
     * @param stacktrace
     */
    public static void writeCrashToFile(String stacktrace) {
        BufferedWriter bos = null;
        File dir = null, redditFile = null;
        FileWriter writer = null;
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(PropertiesReader.getProperty("formatCrashDetail"));
            String timestamp = dateFormat.format(new Date());
            String fileName = PropertiesReader.getProperty("fileNameCrashes") + "_" + timestamp + ".txt";
            String dirRedditRiseCrashes = PropertiesReader.getProperty("dirCrashes");
            dir = new File(Environment.getExternalStorageDirectory() + dirRedditRiseCrashes);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            redditFile =
                    new File(Environment.getExternalStorageDirectory() +
                            dirRedditRiseCrashes, fileName);
            writer = new FileWriter(redditFile, true);
            bos = new BufferedWriter(writer);
            bos.write(stacktrace);
            bos.flush();
            bos.close();

        } catch (Exception e) {
            if (Constants.DEBUG) {
                e.printStackTrace();
                Log.e(TAG, PropertiesReader.getProperty("errorReading") + " " + e.getMessage());
            }
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                    bos = null;
                } catch (Exception ignored) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                    writer = null;
                } catch (Exception ignored) {
                }
            }
            if (redditFile != null) {
                redditFile = null;
            }
            if (dir != null) {
                dir = null;
            }
            if (dateFormat != null) {
                dateFormat = null;
            }
        }
    }

    /**
     * @param exception
     */
    public static void LOGE(Throwable exception) {
        BufferedWriter bos = null;
        File dir = null, redditRiseFile = null;
        FileWriter writer = null;
        SimpleDateFormat dateFormat = null;
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            String sLOG = PropertiesReader.getProperty("LOG_ERROR");
            if (Constants.OFF_.equals(sLOG)) {
                return;
            }

            sw = new StringWriter();
            pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            String exceptionAsString = sw.toString();

            dateFormat = new SimpleDateFormat(PropertiesReader.getProperty("formatLogFile"));
            String timestamp = dateFormat.format(new Date());
            String fileName = PropertiesReader.getProperty("fileNameErrors") + "_" + timestamp + ".txt";
            String dirRedditRiseCrashes = PropertiesReader.getProperty("dirErrors");
            dir = new File(Environment.getExternalStorageDirectory() + dirRedditRiseCrashes);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            redditRiseFile =
                    new File(Environment.getExternalStorageDirectory() +
                            dirRedditRiseCrashes, fileName);
            writer = new FileWriter(redditRiseFile, true);
            bos = new BufferedWriter(writer);
            bos.write(exceptionAsString);
            bos.flush();
            bos.close();

        } catch (Exception e) {
            if (Constants.DEBUG) {
                e.printStackTrace();
                Log.e(TAG, PropertiesReader.getProperty("errorReading") + " " + e.getMessage());
            }
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                    bos = null;
                } catch (Exception ignored) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                    writer = null;
                } catch (Exception ignored) {
                }
            }
            if (redditRiseFile != null) {
                redditRiseFile = null;
            }
            if (dir != null) {
                dir = null;
            }
            if (dateFormat != null) {
                dateFormat = null;
            }

            if (pw != null) {
                try {
                    pw.close();
                    pw = null;
                } catch (Exception ignored) {
                }
            }

            if (sw != null) {
                try {
                    sw.close();
                    sw = null;
                } catch (Exception ignored) {
                }
            }
        }
    }

}